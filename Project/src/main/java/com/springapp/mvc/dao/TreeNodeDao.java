package com.springapp.mvc.dao;

import com.springapp.mvc.model.NodeDTO;
import com.springapp.mvc.model.TreeNode;

import java.util.List;

/**
 * Created by rpanait on 8/18/2015.
 */
public interface TreeNodeDao {
    void insertNode(TreeNode treeNode);
    void deleteNode(Integer id);
    void updateNode(TreeNode treeNode);
    TreeNode findByTreeNodeId (Integer id);
    TreeNode getRootNode();
    List<TreeNode> findAll();
}
