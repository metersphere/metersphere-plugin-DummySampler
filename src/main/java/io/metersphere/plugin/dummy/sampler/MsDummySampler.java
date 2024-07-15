package io.metersphere.plugin.dummy.sampler;

import io.metersphere.plugin.core.MsParameter;
import io.metersphere.plugin.core.MsTestElement;
import io.metersphere.plugin.core.utils.LogUtil;
import io.metersphere.plugin.dummy.utils.ElementUtil;
import kg.apc.jmeter.samplers.DummySampler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class MsDummySampler extends MsTestElement {
    public MsDummySampler() {
    }

    private String clazzName = "io.metersphere.plugin.dummy.sampler.MsDummySampler";

    private String resp_code;
    private String resp_msg;
    private String connect_time;
    private String latency;
    private String resp_time;
    private Boolean waiting;
    private String req_data;
    private String resp_data;
    private String url;
    private Boolean successful;

    @Override
    public void toHashTree(HashTree tree, List<MsTestElement> hashTree, MsParameter config) {
        LogUtil.info("===========开始转换MsDummySampler ==================");
        if (!this.isEnable()) {
            return;
        }
        DummySampler initDummySampler = initDummySampler();
        if (initDummySampler != null) {
            final HashTree dummySamplerHashTree = tree.add(initDummySampler);
            if (hashTree != null) {
                for (MsTestElement el : hashTree) {
                    el.toHashTree(dummySamplerHashTree, el.getHashTree(), config);
                }
            }
            LogUtil.info("=========== 对象组装完成  ==================");
        } else {
            LogUtil.error("Connect Sampler 生成失败");
            throw new RuntimeException("Connect Sampler生成失败");
        }
    }

    public DummySampler initDummySampler() {
        try {
            DummySampler dummySampler = new DummySampler();
            String id = this.getId() != null && !Objects.equals(this.getId(), "") ? this.getId() : this.getResourceId();
            ElementUtil.setBaseParams(dummySampler, this.getParent(), id, this.getIndex());
            dummySampler.setEnabled(this.isEnable());
            dummySampler.setName(this.getName());

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
            dummySampler.setProperty("WAITING", this.getWaiting() != null ? this.getWaiting() : true);
            dummySampler.setProperty("SUCCESFULL", this.getSuccessful() != null ? this.getSuccessful() : true);
            dummySampler.setProperty("RESULT_CLASS", "org.apache.jmeter.samplers.SampleResult");

            return dummySampler;
        } catch (Exception e) {
            LogUtil.error("MsDummySampler init error: " + e.getMessage());
            return null;
        }
    }
}
