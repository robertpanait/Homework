package com.springapp.mvc.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rpanait on 8/18/2015.
 */
/**/
@Entity
@Cacheable(value = true)
@NamedQueries({
        @NamedQuery(name = "TreeNode.all", query = "select t from TreeNode t "),
        @NamedQuery(name = "TreeNode.selectRoot", query = "select t from TreeNode t where t.parent.id is null")
})
public class TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private TreeNode parent;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent",fetch = FetchType.LAZY)
    private List<TreeNode> children;
    private String name;
    private String json;

    public TreeNode() {

    }

    public TreeNode(TreeNode parent, List<TreeNode> children, String name, String json) {
        this.parent = parent;
        this.children = children;
        this.name = name;
        this.json = json;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


}
