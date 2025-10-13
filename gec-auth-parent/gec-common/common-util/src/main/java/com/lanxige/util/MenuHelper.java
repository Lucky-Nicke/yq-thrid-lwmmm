package com.lanxige.util;


import com.lanxige.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    public static List<SysMenu> buildTree(List<SysMenu> menuList) {
        List<SysMenu> treeList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if(menu.getParentId().longValue() == 0) {
                treeList.add(findChildren(menu, menuList));
            }
        }
        return treeList;
    }

    private static SysMenu findChildren(SysMenu menu, List<SysMenu> treeNodes) {
        menu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu it:treeNodes) {
            if(menu.getId()==it.getParentId()) {
                if (menu.getChildren() == null) {
                    menu.setChildren(new ArrayList<SysMenu>());
                }
                menu.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return menu;
    }
}
