package it.polito.tdp.music.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.music.db.MusicDAO;

public class Model {
	private MusicDAO dao;
	private List<Artist> artisti;
	private SimpleWeightedGraph<Country,DefaultWeightedEdge> graph;
	
	public Model() {
		super();
		dao=new MusicDAO();
	}




	public List<Month> getMesi() {
		
		return dao.getMesi();
	}
	
	public List<ArtistAndNum> getClassificaForMese(int mese){
	 List<ArtistAndNum> ltemp=dao.getClassificaforMese(mese);
	 artisti=new ArrayList<>();
	
	 for(ArtistAndNum an: ltemp){
		 artisti.add(an.getArtist());
	 }
				
	return ltemp;
	}
	
	public void creaGrafo(int mese){
		graph=new SimpleWeightedGraph<Country,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Set<Country> countries= new HashSet<Country>();
		
		for(Artist a: artisti){
			countries.addAll(dao.getCountryForArtist(mese,a));
		}
		
		Graphs.addAllVertices(graph, countries);
		
		for(Country c1: graph.vertexSet()){
			for(Country c2: graph.vertexSet()){
				if(!c1.equals(c2)){
					int peso=dao.getArtistiForCountries(c1,c2,mese);
					if(peso!=0){
						DefaultWeightedEdge e=graph.addEdge(c1, c2);
						if(e!=null){
							graph.setEdgeWeight(e, peso);
						}
					}
				}
			}
		}
		
		
	}
	
	public DefaultWeightedEdge getMax(int mese){
		this.creaGrafo(mese);
		int max=Integer.MIN_VALUE;
		DefaultWeightedEdge best=null;
		for(Country c: graph.vertexSet()){
			for(DefaultWeightedEdge e:graph.edgesOf(c)){
				if(graph.getEdgeWeight(e)>max){
					max=(int) graph.getEdgeWeight(e);
					best=e;
				}
			}
		}
		
		return best;
	}

}
