package com.pubmine.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pubmine.model.Mesh;
import com.pubmine.model.MeshTreeSentence;

@Repository
public interface MeshRepository {

	@Insert(INSERT_MESH_TRREE)
	public boolean saveMeshTree(@Param("meshes") List<MeshTreeSentence> meshes, @Param("tableName") String tableName);
	
	@Insert(INSERT)
	public boolean save(@Param("meshes") List<Mesh> meshes, @Param("tableName") String table);
	
	String INSERT= ""
			+ "<script>"
			+ "		INSERT INTO ${tableName} ("
			+ "			id,"
			+ "			keyword"
			+ "		)"
			+ "		VALUES"
			+ "		<foreach collection='meshes' item='mesh' separator=','>"
			+ "			(#{mesh.id}, #{mesh.keyword})"
			+ "		</foreach>"
			+ "</script>";
	
	String INSERT_MESH_TRREE= ""
			+ "<script>"
			+ "		INSERT INTO ${tableName} ("
			+ "			id,"
			+ "			pmid,"
			+ "			sentence_order"
			+ "		)"
			+ "		VALUES"
			+ "		<foreach collection='meshes' item='mesh' separator=','>"
			+ "			(#{mesh.id}, #{mesh.pmid}, #{mesh.sentenceOrder})"
			+ "		</foreach>"
			+ "</script>";
	
}
