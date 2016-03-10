package com.xnx3.j2ee.shiro;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.entity.Permission;
import com.xnx3.j2ee.bean.PermissionMark;
import com.xnx3.j2ee.bean.PermissionTree;

/**
 * shiro权限相关用到的函数
 * @author 管雷鸣
 *
 */
public class ShiroFunc {
	
	/**
	 * 将数据库查询的Permission的list转换为tree树的形式，分级。
	 * @param myList 要标记的已选择的
	 * @param allList 所有要显示出来的
	 * @return
	 */
	public List<PermissionTree> PermissionToTree(List<Permission> myList,List<Permission> allList){
		List<PermissionMark> listMark = new ArrayList<PermissionMark>();
		
		//整理所有要显示的资源列表，那些标记选中，那些标记不选中
		for (int i = 0; i < allList.size(); i++) {
			Permission ap = allList.get(i);
			PermissionMark pm = new PermissionMark();
			pm.setPermission(ap);
			
			for (int j = 0; j < myList.size(); j++) {
				Permission mp = myList.get(j);
				if(mp.getId()==ap.getId()){
					pm.setSelected(true);
					break;
				}
			}
			listMark.add(pm);
		}
		
		
		//转换为树状数据输出
		List<PermissionTree> permissionTreeList = new ArrayList<PermissionTree>();
		for (int i = 0; i < listMark.size(); i++) {
			PermissionMark pm = listMark.get(i);
			if(pm.getPermission().getParentId()==0){
				PermissionTree permissionTree = new PermissionTree();
				permissionTree.setPermissionMark(pm);
				List<PermissionMark> markTreeList = new ArrayList<PermissionMark>();
				for (int j = 0; j < listMark.size(); j++) {
					PermissionMark permissionMarkSub = listMark.get(j);
					if(permissionMarkSub.getPermission().getParentId()==pm.getPermission().getId()){
						markTreeList.add(permissionMarkSub);
					}
				}
				permissionTree.setList(markTreeList);
				permissionTreeList.add(permissionTree);
			}
		}
		
		return permissionTreeList;
	}

}
