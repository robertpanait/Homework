package com.springapp.mvc.dao;

import com.springapp.mvc.model.TreeNode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by rpanait on 8/18/2015.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class TreeNodeDaoImpl implements TreeNodeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insertNode(TreeNode treeNode) {
        em.persist(treeNode);
    }

    @Override
    public void deleteNode(Integer id) {
        TreeNode treeNode = findByTreeNodeId(id);
        if (treeNode != null) {
            em.remove(treeNode);
        }
    }

    @Override
    public void updateNode(TreeNode node) {
        node.setJson(node.getJson());
    }

    @Override
    public TreeNode findByTreeNodeId(Integer id) {
        return em.find(TreeNode.class, id);
    }

    @Override
    public TreeNode getRootNode() {
        TreeNode treeNode =(TreeNode)em.createNamedQuery("TreeNode.selectRoot").setHint("org.hibernate.cacheable",true).setHint("org.hibernate.cacheMode", "NORMAL").getSingleResult();
        //.createCriteria(TreeNode.class).add(Restrictions.eq("parentId", null)).uniqueResult();
        return treeNode;
    }

    @Override
    public List<TreeNode> findAll() {
        return em.createNamedQuery("TreeNode.all").setHint("org.hibernate.cacheable",true).setHint("org.hibernate.cacheMode","NORMAL").getResultList();
    }
}

