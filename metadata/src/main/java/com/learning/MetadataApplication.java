package com.learning;

import com.learning.metadata.context.TypeContext;
import com.learning.metadata.deparated.CustomTypeHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetadataApplication {

    public static void main(String[] args) throws Exception{
        //SpringApplication.run(MetadataApplication.class, args);

        String text = "[{\"category\":\"Device.Device\",\"fields\":[{\"code\":\"000\",\"id\":\"Relations\",\"items\":{\"refId\":50,\"type\":\"ref\"},\"name\":\"传动关系\",\"type\":\"array\",\"value\":[{\"from\":\"Components.Generator2.Components.Shaft\",\"to\":\"Components.Coupling2\",\"type\":\"2\"},{\"from\":\"Components.Coupling2\",\"to\":\"Components.Shaft2_1.Components.Shaft\",\"type\":\"2\"},{\"from\":\"Components.Generator1.Components.Shaft\",\"to\":\"Components.Coupling1\",\"type\":\"2\"},{\"from\":\"Components.Coupling1\",\"to\":\"Components.Shaft1_1.Components.Shaft\",\"type\":\"2\"},{\"from\":\"Components.Shaft1_1.Components.Gear\",\"to\":\"Components.Shaft1_2.Components.Gear\",\"type\":\"3\"},{\"from\":\"Components.Shaft1_2.Components.Gear\",\"to\":\"Components.Shaft1_3.Components.Gear\",\"type\":\"3\"},{\"from\":\"Components.Shaft2_1.Components.Gear\",\"to\":\"Components.Shaft2_2.Components.Gear\",\"type\":\"3\"},{\"from\":\"Components.Shaft2_2.Components.Gear\",\"to\":\"Components.Shaft2_3.Components.Gear\",\"type\":\"3\"}]}],\"id\":422,\"properties\":[{\"code\":\"1\",\"id\":\"Components\",\"name\":\"组件集合\",\"properties\":[{\"id\":\"Generator1\",\"name\":\"电机1\",\"refId\":2,\"type\":\"ref\"},{\"id\":\"Coupling1\",\"name\":\"传动侧电机联轴器1\",\"refId\":17,\"type\":\"ref\"},{\"id\":\"Shaft1_1\",\"name\":\"传动侧轴1\",\"refId\":423,\"type\":\"ref\"},{\"id\":\"Shaft1_2\",\"name\":\"传动侧轴2\",\"refId\":423,\"type\":\"ref\"},{\"id\":\"Shaft1_3\",\"name\":\"传动侧轴3\",\"refId\":423,\"type\":\"ref\"},{\"id\":\"Generator2\",\"name\":\"电机2\",\"refId\":2,\"type\":\"ref\"},{\"id\":\"Coupling2\",\"name\":\"操作侧电机联轴器2\",\"refId\":17,\"type\":\"ref\"},{\"id\":\"Shaft2_1\",\"name\":\"操作侧轴1\",\"refId\":423,\"type\":\"ref\"},{\"id\":\"Shaft2_2\",\"name\":\"操作侧轴2\",\"refId\":423,\"type\":\"ref\"},{\"id\":\"Shaft2_3\",\"name\":\"操作侧轴3\",\"refId\":423,\"type\":\"ref\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"000\",\"id\":\"Basic\",\"name\":\"基本属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"001\",\"id\":\"Necessary\",\"name\":\"必要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"002\",\"id\":\"Important\",\"name\":\"重要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"003\",\"id\":\"Secondary\",\"name\":\"次要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"004\",\"id\":\"Parameters\",\"name\":\"参数\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"00\",\"id\":\"MeasurementPoints\",\"items\":{\"type\":\"ref\"},\"name\":\"测点集合\",\"readonly\":true,\"type\":\"array\"},{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"ModelCode\",\"name\":\"算法编码\",\"type\":\"text\"}],\"type\":\"object\"},{\"category\":\"Device.Component\",\"events\":[],\"id\":2,\"properties\":[{\"id\":\"Components\",\"name\":\"组件集合\",\"properties\":[{\"id\":\"FrontBearing\",\"name\":\"前轴承\",\"refId\":16,\"type\":\"ref\"},{\"id\":\"RearBearing\",\"name\":\"后轴承\",\"refId\":16,\"type\":\"ref\"},{\"id\":\"Shaft\",\"name\":\"轴\",\"refId\":22,\"type\":\"ref\"}],\"type\":\"object\"},{\"code\":\"000\",\"id\":\"Basic\",\"name\":\"基本属性\",\"properties\":[],\"readonly\":true,\"type\":\"object\"},{\"code\":\"001\",\"id\":\"Necessary\",\"name\":\"必要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"002\",\"id\":\"Important\",\"name\":\"重要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"003\",\"id\":\"Secondary\",\"name\":\"次要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"004\",\"id\":\"Parameters\",\"name\":\"参数\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"00\",\"id\":\"MeasurementPoints\",\"items\":{\"type\":\"ref\"},\"name\":\"测点集合\",\"readonly\":true,\"type\":\"array\"},{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"ModelCode\",\"name\":\"算法编码\",\"type\":\"text\"}],\"services\":[],\"type\":\"object\"},{\"category\":\"Device.Component\",\"events\":[],\"id\":16,\"properties\":[{\"code\":\"000\",\"id\":\"Basic\",\"name\":\"基本属性\",\"properties\":[{\"code\":\"BearingForm\",\"enum\":[{\"label\":\"滚动\",\"value\":\"1\"},{\"label\":\"滑动\",\"value\":\"2\"},{\"label\":\"滚动无参数\",\"value\":\"3\"},{\"label\":\"滑动无参数\",\"value\":\"4\"}],\"id\":\"BearingForm\",\"name\":\"轴承形式\",\"type\":\"enum\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"001\",\"id\":\"Necessary\",\"name\":\"必要属性\",\"properties\":[],\"readonly\":true,\"type\":\"object\"},{\"code\":\"002\",\"id\":\"Important\",\"name\":\"重要属性\",\"properties\":[{\"code\":\"LubricationMode\",\"enum\":[{\"label\":\"脂润滑\",\"value\":\"ZRH\"},{\"label\":\"油浴润滑\",\"value\":\"YYRH\"},{\"label\":\"喷油润滑\",\"value\":\"PYRH\"}],\"id\":\"LubricationMode\",\"name\":\"润滑方式\",\"type\":\"enum\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"003\",\"id\":\"Secondary\",\"name\":\"次要属性\",\"properties\":[],\"readonly\":true,\"type\":\"object\"},{\"code\":\"004\",\"id\":\"Parameters\",\"name\":\"参数\",\"properties\":[{\"enum\":[{\"label\":\"前\",\"value\":\"0\"},{\"label\":\"中\",\"value\":\"1\"},{\"label\":\"后\",\"value\":\"2\"}],\"id\":\"Position\",\"name\":\"轴承位置\",\"type\":\"enum\"},{\"format\":\"double\",\"id\":\"BSF\",\"name\":\"BSF\",\"step\":1,\"type\":\"number\"},{\"format\":\"double\",\"id\":\"FTFO\",\"name\":\"FTFO\",\"step\":1,\"type\":\"number\"},{\"format\":\"double\",\"id\":\"BPFI\",\"name\":\"BPFI\",\"step\":1,\"type\":\"number\"},{\"format\":\"double\",\"id\":\"BPFO\",\"name\":\"BPFO\",\"step\":1,\"type\":\"number\"},{\"code\":\"TYPE\",\"id\":\"TYPE\",\"name\":\"型号\",\"type\":\"number\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"00\",\"id\":\"MeasurementPoints\",\"items\":{\"type\":\"ref\"},\"name\":\"测点集合\",\"readonly\":true,\"type\":\"array\"},{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"ModelCode\",\"name\":\"算法编码\",\"type\":\"text\"}],\"services\":[],\"type\":\"object\"},{\"category\":\"Device.Component\",\"events\":[],\"id\":22,\"properties\":[{\"code\":\"000\",\"id\":\"Basic\",\"name\":\"基本属性\",\"properties\":[{\"code\":\"ShaftStructure\",\"enum\":[{\"label\":\"直轴\",\"value\":\"ZZ\"},{\"label\":\"曲轴\",\"value\":\"QZ\"}],\"id\":\"ShaftStructure\",\"name\":\"轴结构形式\",\"type\":\"enum\"},{\"code\":\"SupportForm\",\"enum\":[{\"label\":\"悬臂\",\"value\":\"XB\"},{\"label\":\"双支撑\",\"value\":\"SZC\"},{\"label\":\"单轴承\",\"value\":\"SINGLE\"},{\"label\":\"双轴承\",\"value\":\"DOUBLE\"}],\"id\":\"SupportForm\",\"name\":\"支撑形式\",\"required\":true,\"type\":\"enum\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"001\",\"id\":\"Necessary\",\"name\":\"必要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"002\",\"id\":\"Important\",\"name\":\"重要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"003\",\"id\":\"Secondary\",\"name\":\"次要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"004\",\"id\":\"Parameters\",\"name\":\"参数\",\"properties\":[{\"code\":\"ShaftDiameter\",\"id\":\"ShaftDiameter\",\"name\":\"轴直径\",\"required\":true,\"type\":\"number\"},{\"code\":\"QZQBGS\",\"format\":\"int\",\"id\":\"QZQBGS\",\"name\":\"曲轴曲柄个数\",\"required\":true,\"type\":\"number\"},{\"code\":\"Length\",\"id\":\"Length\",\"name\":\"长度\",\"type\":\"number\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"00\",\"id\":\"MeasurementPoints\",\"items\":{\"type\":\"ref\"},\"name\":\"测点集合\",\"readonly\":true,\"type\":\"array\"},{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"ModelCode\",\"name\":\"算法编码\",\"type\":\"text\"}],\"services\":[],\"type\":\"object\"},{\"category\":\"Device.Component\",\"events\":[],\"id\":17,\"properties\":[{\"code\":\"000\",\"id\":\"Basic\",\"name\":\"基本属性\",\"properties\":[{\"code\":\"CouplingType\",\"enum\":[{\"label\":\"膜片\",\"value\":\"1\"},{\"label\":\"欧美噶\",\"value\":\"2\"},{\"label\":\"柱销\",\"value\":\"3\"},{\"label\":\"梅花\",\"value\":\"4\"},{\"label\":\"齿套\",\"value\":\"5\"},{\"label\":\"链条\",\"value\":\"6\"},{\"label\":\"皮带\",\"value\":\"7\"},{\"label\":\"液力耦合器\",\"value\":\"8\"},{\"label\":\"万向节\",\"value\":\"9\"},{\"label\":\"连杆\",\"value\":\"10\"},{\"label\":\"涨紧套\",\"value\":\"11\"}],\"id\":\"CouplingType\",\"name\":\"联轴器类型\",\"type\":\"enum\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"001\",\"id\":\"Necessary\",\"name\":\"必要属性\",\"properties\":[{\"code\":\"DiaphragmProperties\",\"enum\":[{\"label\":\"有接手\",\"value\":\"YJS\"},{\"label\":\"无接手\",\"value\":\"WJS\"}],\"id\":\"DiaphragmProperties\",\"name\":\"膜片属性示例\",\"type\":\"enum\"},{\"code\":\"OutputWheelDiameter\",\"id\":\"OutputWheelDiameter\",\"name\":\"输出轮直径\",\"type\":\"number\"},{\"code\":\"InputWheelDiameter\",\"id\":\"InputWheelDiameter\",\"name\":\"输入轮直径\",\"type\":\"number\"},{\"code\":\"BeltLength\",\"id\":\"BeltLength\",\"name\":\"皮带长度\",\"type\":\"number\"},{\"code\":\"Belt\",\"enum\":[{\"label\":\"材质\",\"value\":\"cz\"}],\"id\":\"Belt\",\"name\":\"皮带\",\"type\":\"enum\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"002\",\"id\":\"Important\",\"name\":\"重要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"003\",\"id\":\"Secondary\",\"name\":\"次要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"004\",\"id\":\"Parameters\",\"name\":\"参数\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"00\",\"id\":\"MeasurementPoints\",\"items\":{\"type\":\"ref\"},\"name\":\"测点集合\",\"readonly\":true,\"type\":\"array\"},{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"ModelCode\",\"name\":\"算法编码\",\"type\":\"text\"}],\"services\":[],\"type\":\"object\"},{\"category\":\"Device.Component\",\"id\":423,\"properties\":[{\"code\":\"1\",\"id\":\"Components\",\"name\":\"组件集合\",\"properties\":[{\"id\":\"RearBearing\",\"name\":\"后轴承\",\"refId\":16,\"type\":\"ref\"},{\"id\":\"FrontBearing\",\"name\":\"前轴承\",\"refId\":16,\"type\":\"ref\"},{\"id\":\"Shaft\",\"name\":\"轴\",\"refId\":22,\"type\":\"ref\"},{\"id\":\"Gear\",\"name\":\"齿轮\",\"refId\":23,\"type\":\"ref\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"000\",\"id\":\"Basic\",\"name\":\"基本属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"001\",\"id\":\"Necessary\",\"name\":\"必要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"002\",\"id\":\"Important\",\"name\":\"重要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"003\",\"id\":\"Secondary\",\"name\":\"次要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"004\",\"id\":\"Parameters\",\"name\":\"参数\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"00\",\"id\":\"MeasurementPoints\",\"items\":{\"type\":\"ref\"},\"name\":\"测点集合\",\"readonly\":true,\"type\":\"array\"},{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"ModelCode\",\"name\":\"算法编码\",\"type\":\"text\"}],\"type\":\"object\"},{\"category\":\"Device.Component\",\"events\":[],\"id\":23,\"properties\":[{\"code\":\"000\",\"id\":\"Basic\",\"name\":\"基本属性\",\"properties\":[{\"code\":\"GearForm\",\"enum\":[{\"label\":\"斜齿\",\"value\":\"XC\"},{\"label\":\"直齿\",\"value\":\"ZC\"},{\"label\":\"人字齿\",\"value\":\"RZC\"},{\"label\":\"锥齿\",\"value\":\"ZHC\"},{\"label\":\"蜗轮蜗杆\",\"value\":\"WLWG\"}],\"id\":\"GearForm\",\"name\":\"齿轮形式\",\"type\":\"enum\"},{\"code\":\"GearPosition\",\"enum\":[{\"label\":\"内齿\",\"value\":\"NC\"},{\"label\":\"外齿\",\"value\":\"WC\"}],\"id\":\"GearPosition\",\"name\":\"齿轮位置\",\"type\":\"enum\"},{\"code\":\"TransmissionNumberRatio\",\"enum\":[{\"label\":\"一对一\",\"value\":\"1_1\"},{\"label\":\"一对二\",\"value\":\"1_2\"},{\"label\":\"二对一\",\"value\":\"2_1\"}],\"id\":\"TransmissionNumberRatio\",\"name\":\"传动个数比例\",\"required\":true,\"type\":\"enum\"},{\"code\":\"TransmissionMode\",\"enum\":[{\"label\":\"传动\",\"value\":\"CD\"},{\"label\":\"被传动\",\"value\":\"BCD\"}],\"id\":\"TransmissionMode\",\"name\":\"传动形式\",\"type\":\"enum\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"001\",\"id\":\"Necessary\",\"name\":\"必要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"002\",\"id\":\"Important\",\"name\":\"重要属性\",\"properties\":[{\"code\":\"LubricationMode\",\"enum\":[{\"label\":\"脂润滑\",\"value\":\"ZRH\"},{\"label\":\"油浴润滑\",\"value\":\"YYRH\"},{\"label\":\"喷油润滑\",\"value\":\"PYRH\"}],\"id\":\"LubricationMode\",\"name\":\"润滑方式\",\"type\":\"enum\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"003\",\"id\":\"Secondary\",\"name\":\"次要属性\",\"readonly\":true,\"type\":\"object\"},{\"code\":\"004\",\"id\":\"Parameters\",\"name\":\"参数\",\"properties\":[{\"code\":\"CS\",\"format\":\"int\",\"id\":\"CS\",\"name\":\"齿数\",\"type\":\"number\"}],\"readonly\":true,\"type\":\"object\"},{\"code\":\"00\",\"id\":\"MeasurementPoints\",\"items\":{\"type\":\"ref\"},\"name\":\"测点集合\",\"readonly\":true,\"type\":\"array\"},{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"ModelCode\",\"name\":\"算法编码\",\"type\":\"text\"}],\"services\":[],\"type\":\"object\"},{\"category\":\"Device.Relation\",\"id\":50,\"properties\":[{\"code\":\"000\",\"id\":\"from\",\"name\":\"来源\",\"readonly\":true,\"type\":\"text\"},{\"code\":\"001\",\"id\":\"to\",\"name\":\"目标\",\"readonly\":true,\"type\":\"text\"},{\"enum\":[{\"label\":\"配合\",\"value\":\"1\"},{\"label\":\"连接\",\"value\":\"2\"},{\"label\":\"啮合\",\"value\":\"3\"},{\"label\":\"带传动\",\"value\":\"4\"}],\"id\":\"type\",\"name\":\"类型\",\"type\":\"enum\"},{\"code\":\"00\",\"id\":\"Parameters\",\"name\":\"参数集合\",\"readonly\":true,\"type\":\"object\"}],\"type\":\"object\"},{\"category\":\"Device.Measurement\",\"fields\":[{\"id\":\"ModelCode\",\"name\":\"模型代码\",\"type\":\"text\",\"value\":\"EdgeMeasPoint\"}],\"id\":85,\"properties\":[{\"code\":\"01\",\"id\":\"Indexes\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"指标编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"RelatedPointIds\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"关联测点编号集合\",\"readonly\":true,\"type\":\"array\"},{\"id\":\"MeasurementRange\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"测量范围\",\"type\":\"array\"},{\"id\":\"SuperTermIndex\",\"name\":\"超级指标长度\",\"properties\":[{\"format\":\"double\",\"id\":\"SuperTermVelLength\",\"name\":\"超级指标速度长度\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"SuperTermHighLength\",\"name\":\"超级指标高频长度\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"SuperTermLowLength\",\"name\":\"超级指标低频长度\",\"type\":\"number\"}],\"type\":\"object\"},{\"id\":\"ShortTermIndex\",\"name\":\"短时指标长度\",\"properties\":[{\"format\":\"double\",\"id\":\"ShortTermVelLength\",\"name\":\"短时指标速度长度\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"ShortTermHighLength\",\"name\":\"短时指标高频长度\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"ShortTermLowLength\",\"name\":\"短时指标低频长度\",\"type\":\"number\"}],\"type\":\"object\"},{\"format\":\"double\",\"id\":\"VelHighFreq\",\"name\":\"速度上限频率\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"VelLowFreq\",\"name\":\"速度下限频率\",\"type\":\"number\"},{\"id\":\"HighAccHighFreq\",\"name\":\"高频加速度上限频率\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"HighAccLowFreq\",\"name\":\"高频加速度下限频率\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"LowAccHighFreq\",\"name\":\"低频加速度上限频率\",\"type\":\"number\"},{\"format\":\"double\",\"id\":\"LowAccLowFreq\",\"name\":\"低频加速度下限频率\",\"type\":\"number\"},{\"format\":\"int\",\"id\":\"CouplingId\",\"items\":{\"format\":\"int\",\"type\":\"number\"},\"name\":\"监测联轴器\",\"step\":1,\"type\":\"array\"},{\"enum\":[{\"label\":\"H\",\"value\":\"H\"},{\"label\":\"V\",\"value\":\"V\"},{\"label\":\"A\",\"value\":\"A\"}],\"id\":\"Direaction\",\"name\":\"监测方向\",\"type\":\"enum\"}],\"type\":\"object\"}]";

        TypeContext type = CustomTypeHelper.loadTypeContext(text);

        System.out.println(type);

    }

}
