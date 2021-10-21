package io.metersphere.plugin.dummy.sampler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import io.metersphere.plugin.core.MsParameter;
import io.metersphere.plugin.core.MsTestElement;
import io.metersphere.plugin.core.utils.LogUtil;
import io.metersphere.plugin.dummy.utils.ElementUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import kg.apc.jmeter.samplers.DummySampler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MsDummySampler extends MsTestElement {
    public MsDummySampler() {
    }

    private String clazzName = "io.metersphere.plugin.dummy.sampler.MsDummySampler";

    @JSONField(ordinal = 10)
    private String resp_code;
    @JSONField(ordinal = 11)
    private String resp_msg;
    @JSONField(ordinal = 12)
    private String connect_time;
    @JSONField(ordinal = 13)
    private String latency;
    @JSONField(ordinal = 14)
    private String resp_time;
    @JSONField(ordinal = 15)
    private Boolean waiting;
    @JSONField(ordinal = 16)
    private String req_data;
    @JSONField(ordinal = 17)
    private String resp_data;
    @JSONField(ordinal = 18)
    private String url;
    @JSONField(ordinal = 19)
    private Boolean successful;

    @Override
    public void toHashTree(HashTree tree, List<MsTestElement> hashTree, MsParameter config) {
        LogUtil.info("===========开始转换MsDummySampler ==================");
        if (!this.isEnable()) {
            return;
        }
        DummySampler initDummySampler = initDummySampler();
        if (initDummySampler != null) {
            tree.add(initDummySampler());
            final HashTree mqttTree = tree;
            if (CollectionUtils.isNotEmpty(hashTree)) {
                for (MsTestElement el : hashTree) {
                    el.toHashTree(mqttTree, el.getHashTree(), config);
                }
            }
        } else {
            LogUtil.error("Connect Sampler 生成失败");
            throw new RuntimeException("Connect Sampler生成失败");
        }
    }

    public DummySampler initDummySampler() {
        try {
            DummySampler dummySampler = new DummySampler();
            // base 执行时需要的参数, MsElement 的通用属性
            dummySampler.setProperty("MS-ID", this.getId());
            String indexPath = this.getIndex();
            dummySampler.setProperty("MS-RESOURCE-ID", this.getResourceId() + "_" + ElementUtil.getFullIndexPath(this.getParent(), indexPath));
            List<String> id_names = new LinkedList<>();
            ElementUtil.getScenarioSet(this, id_names);
            dummySampler.setProperty("MS-SCENARIO", JSON.toJSONString(id_names));
            dummySampler.setEnabled(this.isEnable());
            dummySampler.setName(this.getName());

            // jmx 中 DummySampler 对应的 guiclass 和 testclass 属性
            dummySampler.setProperty(TestElement.GUI_CLASS, "kg.apc.jmeter.samplers.DummySamplerGui");
            dummySampler.setProperty(TestElement.TEST_CLASS, "kg.apc.jmeter.samplers.DummySampler");

            // 为 jmx 中 DummySampler 所需的各个属性赋值
            dummySampler.setProperty("RESPONSE_CODE", this.getResp_code());
            dummySampler.setProperty("RESPONSE_MESSAGE", this.getResp_msg());
            dummySampler.setProperty("REQUEST_DATA", this.getReq_data());
            dummySampler.setProperty("RESPONSE_DATA", this.getResp_data());
            dummySampler.setProperty("RESPONSE_TIME", this.getResp_time());
            dummySampler.setProperty("LATENCY", this.getLatency());
            dummySampler.setProperty("CONNECT", this.getConnect_time());
            dummySampler.setProperty("URL", this.getUrl());
            dummySampler.setProperty("WAITING", this.getWaiting());
            dummySampler.setProperty("SUCCESFULL", this.getSuccessful());
            dummySampler.setProperty("RESULT_CLASS", "org.apache.jmeter.samplers.SampleResult");

            return dummySampler;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
