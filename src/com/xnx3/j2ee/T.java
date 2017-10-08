package com.xnx3.j2ee;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Table;





import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.shiro.ShiroFunc;

public class T {
	public static void main(String[] args) {
		
//		Annotations 
//		Annotation[] a = User.class.getAnnotations();
//		for (int i = 0; i < a.length; i++) {
//			System.out.println(a[i]);
//		}
		
		User u = new T().test(User.class);
		System.out.println(u);
		
	}
	
	public <E> E test(Class<E> c){
		Field[] f = getClass().getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			System.out.println(f[i].getName());
		}
//		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();   
//		Class<E> entityClass =  (Class)params[0];   
//		System.out.println(entityClass.getCanonicalName());
		
		
		return (E) new User();
	}
}
