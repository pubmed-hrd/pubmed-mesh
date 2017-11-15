package com.pubmine.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pubmine.model.Mesh;
import com.pubmine.model.MeshTreeSentence;
import com.pubmine.utility.Pagable;

@Service
public class SentenceService {

	private IndexReader reader;
	private IndexSearcher searcher;
	private Analyzer analyzer;
	private QueryParser parser;

	@Value("${pubmine.indexPath}")
	private String indexPath;

	public List<MeshTreeSentence> searchForMeshTree(Mesh mesh, Pagable paging) {
		
		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
			searcher = new IndexSearcher(reader);
			analyzer = new StandardAnalyzer();

			parser = new MultiFieldQueryParser(new String[] { "sentence" }, analyzer);

			Query query;
			try {
				
				if(mesh.getKeyword().equals(""))
					query = new MatchAllDocsQuery();
				else
					query = parser.parse(repaceText(mesh.getKeyword()));
				
				System.out.println("-> Search Query: " + repaceText(mesh.getKeyword()));
				
				TopScoreDocCollector collector = TopScoreDocCollector.create(paging.getPage() * paging.getLimit());
				searcher.search(query, collector);
				
				ScoreDoc[] hits = collector.topDocs(paging.getOffset(), paging.getLimit()).scoreDocs;

				System.out.println("-> Total results: " + collector.getTotalHits());
				List<MeshTreeSentence> meshTreeSentence = new ArrayList<>();

				for (int i = 0; i < hits.length; i++) {
					Document doc = searcher.doc(hits[i].doc);

					Integer pmid = Integer.parseInt(doc.get("pmid"));
					Integer sentenceOrder = Integer.parseInt(doc.get("sentenceOrder"));
					
					meshTreeSentence.add(new MeshTreeSentence(mesh.getId(), pmid, sentenceOrder));
				}
				paging.setTotalCount(collector.getTotalHits());
				
				reader.close();
				
				System.out.println("=> Searching finish!!!");
				
				
				return meshTreeSentence;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String repaceText(String keyword){
		return keyword.replace(" ", " AND ");
	}
	
	public static void main(String[] args) {
		System.out.println(new SentenceService().repaceText("Hello"));
	}
	
}
