package com.springapp.mvc.service;

import com.springapp.mvc.dao.TreeNodeDao;
import com.springapp.mvc.model.NodeDTO;
import com.springapp.mvc.model.TreeNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rpanait on 8/18/2015.
 */
@Service
public class TreeNodeServiceImpl implements TreeNodeService {
    @Autowired
    private TreeNodeDao treeNodeDao;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertNode(NodeDTO nodeDTO) throws BusinessException {
        TreeNode treeNode = new TreeNode();
        Integer parentId = nodeDTO.getParentId();
        if (parentId != null) {
            TreeNode parentNode = new TreeNode();
            parentNode.setId(parentId);
            treeNode.setParent(parentNode);
        }
        treeNode.setName(nodeDTO.getName());
        try {
            JSONObject jsonObj = new JSONObject(nodeDTO.getJson());
            treeNode.setJson(jsonObj.toString());
        }
        catch(JSONException e){
            throw new BusinessException("Invalid format!");
        }
        treeNodeDao.insertNode(treeNode);
    }

    @Override
    @Transactional
    public void deleteNode(Integer treeNodeId) {
        treeNodeDao.deleteNode(treeNodeId);
    }

    @Override
    @Transactional
    public void updateNode(NodeDTO nodeDTO) throws BusinessException {
        if(nodeDTO.getId()!=null){
            TreeNode treeNode = treeNodeDao.findByTreeNodeId(nodeDTO.getId());
            Integer parentId = nodeDTO.getParentId();
            if (parentId != null) {
                TreeNode parentNode = new TreeNode();
                parentNode.setId(parentId);
                treeNode.setParent(parentNode);
            }
            if (nodeDTO.getName() != null) {
                treeNode.setName(nodeDTO.getName());
            }
            try {
                if (nodeDTO.getJson() != null) {
                    JSONObject jsonObj = new JSONObject(nodeDTO.getJson());
                    treeNode.setJson(jsonObj.toString());
                }
            } catch (JSONException e) {
                throw new BusinessException("Invalid format!");
            }
            treeNodeDao.updateNode(treeNode);
        }else
        throw new BusinessException("The id doesn`t exist");
    }

    @Override
    @Transactional
    public NodeDTO findByTreeNodeId(Integer id) throws BusinessException{
        TreeNode treeNode = treeNodeDao.findByTreeNodeId(id);
        if (treeNode==null){
            throw new BusinessException("The id doesn`t exist");
        }
        return new NodeDTO(treeNode);
    }

    @Override
    @Transactional
    public List<NodeDTO> getChildren(Integer id)  throws BusinessException{
        List<NodeDTO> list = new ArrayList<NodeDTO>();
        TreeNode treeNode = treeNodeDao.findByTreeNodeId(id);
        if (treeNode==null){
            throw new BusinessException("The id doesn`t exist");
        }
        List<TreeNode> listTree = treeNode.getChildren();
        for (TreeNode l : listTree) {
            list.add(new NodeDTO(l));
        }

        return list;
    }

    @Override
    @Transactional
    public NodeDTO getRootNode() {
        return new NodeDTO(treeNodeDao.getRootNode());
    }

    @Override
    @Transactional
    public List<NodeDTO> findAll() {
        List<NodeDTO> list = new ArrayList<NodeDTO>();
        List<TreeNode> listTree = treeNodeDao.findAll();
        for (TreeNode l : listTree) {
            list.add(new NodeDTO(l));
        }

        return list;
    }

    @Override
    @Transactional
    public JSONObject getMergedConfiguration(Integer id, boolean bottomUp) {
        JSONObject result=null;
        TreeNode treeNode = treeNodeDao.findByTreeNodeId(id);
        if (treeNode!=null) {
            result=new JSONObject(treeNode.getJson());
            do {
                treeNode = treeNode.getParent();
                if (treeNode != null) {
                    treeNode = treeNodeDao.findByTreeNodeId(treeNode.getId());
                    result=mergeJsons(result,new JSONObject(treeNode.getJson()),bottomUp);
                }
            }
            while (treeNode != null);
        }
        return result;
    }


    private JSONObject mergeJsons(JSONObject child,JSONObject parent, boolean bottomUp){
        JSONObject result=parent;
        if (bottomUp){
            result= child;
            for (String key:JSONObject.getNames(parent)){
                if (!child.has(key)) {
                    child.put(key, parent.get(key));
                }
            }
        }else{
            for (String key:JSONObject.getNames(child)){
                if (!parent.has(key)) {
                    parent.put(key, child.get(key));
                }
            }
        }
        return result;
    }

}
