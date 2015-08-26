package com.springapp.mvc.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapp.mvc.model.NodeDTO;
import com.springapp.mvc.service.BusinessException;
import com.springapp.mvc.service.TreeNodeService;
import com.sun.javafx.sg.prism.NodeEffectInput;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.soap.Node;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController()
@RequestMapping("/")
public class MainController {
    public static final String APPLICATION_JSON = "application/json";
    @Autowired
    private TreeNodeService treeNodeService;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ResponseBody
    public Map<String, String> display(@PathVariable Integer id) throws BusinessException, IOException {
        NodeDTO treeNode = treeNodeService.findByTreeNodeId(id);
        Map finalResult=new TreeMap<String, Object>();
        Map content = objectMapper.readValue(treeNode.getJson(), Map.class);
        finalResult.put("parentId", treeNode.getParentId());
        finalResult.put("parentName", treeNode.getParent());
        finalResult.put("nodeName", treeNode.getName());
        finalResult.put("nodeConfiguration", content);

        return finalResult;
    }




    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    public void insert(@RequestBody NodeDTO nodeDTO) throws BusinessException {
        treeNodeService.insertNode(nodeDTO);
    }

    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)  {
        treeNodeService.deleteNode(id);
    }


    @RequestMapping(value = "/nodes", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody NodeDTO nodeDTO) throws BusinessException {

        treeNodeService.updateNode(nodeDTO);
    }

    /*@RequestMapping(value="/children",method = RequestMethod.GET)
    public String getChildren() {
        List<TreeNode> list = treeNodeService.getChildren("2");
        for(TreeNode l:list){
           System.out.println(l.toString());
       }
        return "page";
    }*/
    @RequestMapping(value = "/nodes/{id}/children", method = RequestMethod.GET)
    public List<NodeDTO> getChildren(@PathVariable Integer id) throws BusinessException{
        return treeNodeService.getChildren(id);
    }


    @RequestMapping(value = "/root", method = RequestMethod.GET)
    public NodeDTO getRoot() throws IOException {
        NodeDTO nodeDTO = treeNodeService.getRootNode();
        return nodeDTO;
    }

    @RequestMapping(value = "/nodes/{id}/parent", method = RequestMethod.GET)
    public NodeDTO getParent(@PathVariable Integer id) throws BusinessException{
        NodeDTO nodeDTO = null;
        if (!treeNodeService.findByTreeNodeId(id).getId().equals(treeNodeService.getRootNode().getId())) {
            nodeDTO = treeNodeService.findByTreeNodeId(id);
        }
        return nodeDTO;
    }

    @RequestMapping(value = "/nodes/{id}/configuration", method = RequestMethod.GET,produces = APPLICATION_JSON)
    @ResponseBody
    public Map<String, String> getConfiguration(@PathVariable Integer id) throws IOException, BusinessException {
        NodeDTO nodeDTO = treeNodeService.findByTreeNodeId(id);
        return objectMapper.readValue(nodeDTO.getJson(), Map.class);
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET,produces = APPLICATION_JSON)
    @ResponseBody
    public List<NodeDTO> findAll() throws IOException {
        return treeNodeService.findAll();
    }

    @RequestMapping(value = "/nodes/{id}/mergedconfig/{bottomUp}", method = RequestMethod.GET,produces = APPLICATION_JSON)
    @ResponseBody
    public String getMergedConfiguration(@PathVariable Integer id,@PathVariable Boolean bottomUp) throws IOException {
        return treeNodeService.getMergedConfiguration(id,bottomUp).toString();
    }

}