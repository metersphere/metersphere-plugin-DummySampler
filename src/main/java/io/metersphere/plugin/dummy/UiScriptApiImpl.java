package io.metersphere.plugin.dummy;

import io.metersphere.plugin.core.api.UiScriptApi;
import io.metersphere.plugin.core.ui.PluginResource;
import io.metersphere.plugin.core.ui.UiScript;
import io.metersphere.plugin.core.utils.LogUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class UiScriptApiImpl extends UiScriptApi {

    @Override
    public PluginResource init() {
        LogUtil.info("开始初始化 DummySampler 插件脚本内容 ");
        List<UiScript> uiScripts = new LinkedList<>();

        // 获取表单 json
        String dummySampler = getJson("/json/ui_dummy.json");

        // 每个 UiScript 对象对应接口自动化中的一个步骤
        UiScript dummyScript = new UiScript("dummy_sampler", "模拟请求", "io.metersphere.plugin.dummy.sampler.MsDummySampler", dummySampler);

        // 指定对应的 JMeter 基类，用于进行是否可添加到
        dummyScript.setJmeterClazz("AbstractSampler");

        // 指定表单配置
        dummyScript.setFormOption(getJson("/json/ui_form.json"));

        // 一个插件提供多个步骤时，构造并添加多个 UiScript 对象
        uiScripts.add(dummyScript);
        LogUtil.info("初始化 DummySampler 插件脚本内容结束 ");
        return new PluginResource("dummy-v1.0.2", uiScripts);
    }

    public String getJson(String path) {
        try (InputStream in = UiScriptApiImpl.class.getResourceAsStream(path)) {
            if (in != null) {
                return IOUtils.toString(in, StandardCharsets.UTF_8);
            } else {
                LogUtil.error("Resource not found: " + path);
            }
        } catch (IOException ex) {
            LogUtil.error("Failed to read resource: " + path, ex);
        }
        return null;
    }


    @Override
    public String customMethod(String req) {
        LogUtil.info("执行 MsDummySampler 插件中的自定义方法");
        return null;
    }
}
