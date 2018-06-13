package com.zhbit.cms.frameclass;

import com.zhbit.cms.tools.ClassTools;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

public class PermissionsParsing {
    private static class InstanceClass {
        private static final PermissionsParsing instance=new PermissionsParsing();
    }
    private PermissionsParsing(){
        init();
    }
    public static PermissionsParsing getInstance(){
        return InstanceClass.instance;
    }
    private PermissionTree permissionTree=null;


    private void init(){
        try {
            File file= ClassTools.relativePaths("com/zhbit/cms/filter/PermissionConfig.xml");
            Document document=new SAXReader().read(file);
            permissionTree=new PermissionTree(document.getRootElement());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public int getLevel(int userGroup,String pathInfo){
        return permissionTree.getLevel(userGroup,pathInfo);
    }


}
