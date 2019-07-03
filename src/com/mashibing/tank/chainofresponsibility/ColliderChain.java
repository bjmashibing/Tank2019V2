package com.mashibing.tank.chainofresponsibility;

import com.mashibing.tank.AbstractGameObject;
import com.mashibing.tank.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

public class ColliderChain implements Collider {
    private List<Collider> colliders;

    public ColliderChain() {
        initColliders();
    }

    private void initColliders() {
        colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        try {
            for(String name : colliderNames) {
                Class clazz = Class.forName("com.mashibing.tank.chainofresponsibility." + name);
                Collider c = (Collider)(clazz.getConstructor().newInstance());
                colliders.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        for(Collider collider : colliders) {
            if(!collider.collide(go1, go2)) {
               return false;
            }
        }
        return true;
    }
}
