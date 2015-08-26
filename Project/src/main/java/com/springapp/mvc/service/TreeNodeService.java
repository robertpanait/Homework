package com.springapp.mvc.service;

import com.springapp.mvc.model.NodeDTO;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by rpanait on 8/18/2015.
 */
public interface TreeNodeService {
    void insertNode(NodeDTO node) throws BusinessException;
    void deleteNode(Integer id);
    void updateNode(NodeDTO node) throws BusinessException;
    NodeDTO findByTreeNodeId (Integer id)  throws BusinessException;
    List<NodeDTO> getChildren (Integer id)  throws BusinessException;
    NodeDTO getRootNode();
    List<NodeDTO> findAll();
    JSONObject getMergedConfiguration(Integer id,boolean bottomUp);

}
