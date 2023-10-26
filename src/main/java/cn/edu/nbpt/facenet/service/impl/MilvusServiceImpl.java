package cn.edu.nbpt.facenet.service.impl;

import cn.edu.nbpt.facenet.inception.FaceDetection;
import cn.edu.nbpt.facenet.inception.FaceDetector;
import cn.edu.nbpt.facenet.model.InceptionResNetV1;
import cn.edu.nbpt.facenet.service.MilvusService;
import io.milvus.client.MilvusServiceClient;
import io.milvus.exception.ParamException;
import io.milvus.grpc.DataType;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.param.*;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.dml.DeleteParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.partition.CreatePartitionParam;
import io.milvus.param.partition.LoadPartitionsParam;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MilvusServiceImpl implements MilvusService {

    //要连接和创建数据库用的变量
    private static MilvusServiceClient milvusClient = null;
    private static FieldType fieldType1 = null;
    private static FieldType fieldType2 = null;
    private static CreateCollectionParam createCollectionReq = null;

    //进行File转INDArray要用的数据
    private static final Logger log = LoggerFactory.getLogger(FaceDetection.class);
    private FaceDetector detector;
    private ComputationGraph graph;
    private final NativeImageLoader loader = new NativeImageLoader();

    //准备索引参数要用的数据
    private final IndexType INDEX_TYPE = IndexType.IVF_FLAT;   // IndexType：用于加速向量搜索的索引类型。
    private final String INDEX_PARAM = "{\"nlist\":1024}";     // ExtraParam：构建特定于索引的参数。

    //准备搜索参数要用的数据
    private final Integer SEARCH_K = 1;                       // TopK：输出向量结果数
    private final String SEARCH_PARAM = "{\"nprobe\":10}";    // Params：该索引特有的搜索参数


    @Value("${milvus.host}")
    private String milvusHost;
    @Value("${milvus.port}")
    private int milvusPort;
    @Value("${milvus.collectionName}")
    private String collectionName;
    @Value("${milvus.partitionName}")
    private String partitionName;

    @PostConstruct
    public void test() throws Exception {

        detector = new FaceDetector();
        InceptionResNetV1 v1 = new InceptionResNetV1();
        v1.init();
        v1.loadWeightData();
        graph = v1.getGraph();

        //连接到 Milvus 服务器
        milvusClient = new MilvusServiceClient(
                ConnectParam.newBuilder()
                        .withHost(milvusHost)
                        .withPort(milvusPort)
                        .build());
//        createCollection();
    }

    @Override
    public void createCollection() {


        //创建 Collection
        fieldType1 = FieldType.newBuilder()
                .withName("user_id")//要创建的字段的描述。
                .withDataType(DataType.Int64)//要创建的字段的数据类型
                .withPrimaryKey(true)//切换控制字段是否为主键字段
                .withAutoID(false)//切换以启用或禁用自动 ID（主键）分配
                .build();
        fieldType2 = FieldType.newBuilder()
                .withName("user_intro")
                .withDataType(DataType.FloatVector)
                .withDimension(128)//向量的维度（长度）
                .withPrimaryKey(false)
                .withAutoID(false)
                .build();

        createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .withDescription("facial recognition technology")
                .withShardsNum(2)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .build();

        R<RpcStatus> r = milvusClient.createCollection(createCollectionReq);
        System.out.println(r);

        //创建 Partition
        r = milvusClient.createPartition(
                CreatePartitionParam.newBuilder()
                        .withCollectionName(collectionName)//待创建 Partition 的 Collection 名称。
                        .withPartitionName(partitionName)//待创建的 Partition 名称。
                        .build());
        System.out.println(r);


    }

    @Override
    public void clear() {
        //加载 Partition
        milvusClient.loadPartitions(
                LoadPartitionsParam.newBuilder()
                        .withCollectionName(collectionName)//要从中加载分区的集合的名称。
                        .withPartitionNames(Collections.singletonList(partitionName))//要加载的分区的名称列表。
                        .build());

        milvusClient.dropCollection(
                DropCollectionParam.newBuilder()
                        .withCollectionName(collectionName)
                        .build());
    }


    //添加milvus数据库数据
    @Override
    public void milvusAdd(Integer id, File file) {

        //加载 Partition
        milvusClient.loadPartitions(
                LoadPartitionsParam.newBuilder()
                        .withCollectionName(collectionName)//要加载的 collection 名称。
                        .withPartitionNames(Collections.singletonList(partitionName))//要加载的分区的名称列表。
                        .build());

        //把载入的id变化我想要的数据
        List<Long> user_id_array = new ArrayList<>();
        String s = String.valueOf(id);
        Long value = Long.valueOf(s);
        user_id_array.add(value);

        //把载入的File变成我想要的数据
        List<float[]> user_intro = this.getFaceVector(file);
        List<List<Float>> user_intro_array = new ArrayList<>();
        float[] floats = user_intro.get(0);
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < floats.length; i++) {
            list.add(floats[i]);
        }
        user_intro_array.add(list);

        //向 Milvus 插入数据
        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field("user_id", DataType.Int64, user_id_array));
        fields.add(new InsertParam.Field("user_intro", DataType.FloatVector, user_intro_array));
        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionName(partitionName)
                .withFields(fields)
                .build();
        R<MutationResult> insert = milvusClient.insert(insertParam);
        System.out.println("添加是否成功");
        System.out.println(insert);

    }

    //删除milvus数据库数据
    @Override
    public void milvusDelete(Integer id) {

        milvusClient.loadPartitions(
                LoadPartitionsParam.newBuilder()
                        .withCollectionName(collectionName)//要加载的 collection 名称。
                        .withPartitionNames(Collections.singletonList(partitionName))//要加载的分区的名称列表。
                        .build());

        String delete_expr = "user_id in [" + id + "]";

        milvusClient.delete(
                DeleteParam.newBuilder()
                        .withCollectionName(collectionName)
                        .withPartitionName(partitionName)
                        .withExpr(delete_expr)
                        .build());

    }

    //进行人脸搜索对比
    @Override
    public int milvusSelect(File file) {
        milvusClient.loadPartitions(
                LoadPartitionsParam.newBuilder()
                        .withCollectionName(collectionName)//要加载的 collection 名称。
                        .withPartitionNames(Collections.singletonList(partitionName))//要加载的分区的名称列表。
                        .build());

        List<String> search_output_fields = Arrays.asList("user_id");//要返回的 field 名称。

        //把载入的File变成我想要的数据
        List<float[]> user_intro = getFaceVector(file);
        List<List<Float>> search_vectors = new ArrayList<>();
        float[] floats = user_intro.get(0);
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < floats.length; i++) {
            list.add(floats[i]);
        }
        search_vectors.add(list);

        //进行向量搜索
//        System.out.println("--------------------------进行向量搜索--------------------------");
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionNames(Arrays.asList(partitionName))
                .withMetricType(MetricType.L2)
                .withOutFields(search_output_fields)
                .withTopK(SEARCH_K)
                .withVectors(search_vectors)
                .withVectorFieldName("user_intro")
                .build();
        R<SearchResults> respSearch = milvusClient.search(searchParam);

//        System.out.println(respSearch);

//        respSearch.getData().getResults().get
        int row = 0;
        try {
            float scores = respSearch.getData().getResults().getScores(0);//对比的参数
            long data = respSearch.getData().getResults().getFieldsData(0).getScalars().getLongData().getData(0);
            String s = String.valueOf(data);
            Integer id = Integer.valueOf(s);

            if (scores < 0.3F) {
                row = id;
            }

        } catch (Exception e) {

        }
        return row;
    }


    //把 File 转 INDArray
    private INDArray getFaceFactor(File img) throws Exception {
        INDArray srcImg = loader.asMatrix(img);
        INDArray[] detection = detector.getFaceImage(srcImg, 160, 160);
        if (detection == null) {
            log.warn("no face detected in image file:{}", img);
            return null;
        }
        if (detection.length > 1) {
            log.warn("{} faces detected in image file:{}, the first detected face will be used.",
                    detection.length, img);
        }
        if (log.isDebugEnabled()) {
            ImageIO.write(detector.toImage(detection[0]), "jpg", new File(img.getName()));
        }
        INDArray output[] = graph.output(InceptionResNetV1.prewhiten(detection[0]));
        return output[1];
    }

    public List<float[]> getFaceVector(File file) {
        List<float[]> list1 = new ArrayList<>();
        try {
            INDArray array = this.getFaceFactor(file);
            list1 = Arrays.asList(array.data().asFloat());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list1;
    }


}
