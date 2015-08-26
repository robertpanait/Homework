package com.springapp.mvc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpanait on 8/19/2015.
 */
public class NodeDTO {
    private Integer id;
    private String name;
    private String json;

    private String parent;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    private Integer parentId;

    public NodeDTO() {
    }

    public NodeDTO(TreeNode treeNode){
        if(treeNode != null) {
            this.id = treeNode.getId();
            this.json = treeNode.getJson();
            this.name = treeNode.getName();
            TreeNode parent = treeNode.getParent();
            if (parent != null) {
                this.parent = parent.getName();
                this.parentId=parent.getId();
            }

        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


}
