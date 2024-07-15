package io.metersphere.plugin.dummy.utils;

import io.metersphere.plugin.core.MsTestElement;
import io.metersphere.plugin.core.utils.LogUtil;
import kg.apc.jmeter.samplers.DummySampler;

public class ElementUtil {

    public static String getFullIndexPath(MsTestElement element, String path) {
        if (element == null || element.getParent() == null) {
            return path;
        }
        path = element.getIndex() + "_" + path;
        return getFullIndexPath(element.getParent(), path);
    }

    private static String getResourceId(String resourceId, MsTestElement parent, String indexPath) {
        return resourceId + "_" + ElementUtil.getFullIndexPath(parent, indexPath);
    }


    public static void setBaseParams(DummySampler sampler, MsTestElement parent, String id, String indexPath) {
        sampler.setProperty("MS-ID", id);
        sampler.setProperty("MS-RESOURCE-ID", ElementUtil.getResourceId(id, parent, indexPath));
        LogUtil.info("mqtt sampler resourceId :" + sampler.getPropertyAsString("MS-RESOURCE-ID"));
    }
}
