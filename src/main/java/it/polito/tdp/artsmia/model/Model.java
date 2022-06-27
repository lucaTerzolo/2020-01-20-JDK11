package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private ArtsmiaDAO dao;
	private Graph<Artist,DefaultWeightedEdge> grafo;
	private Map<Integer,Artist> idMap;
	private List<Artist> best;
	
	public Model() {
		dao=new ArtsmiaDAO();
	}
	
	public String creaGrafo(String role) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getArtistByRole(role));
		idMap=new HashMap<>();
		for(Artist a:this.grafo.vertexSet())
			idMap.put(a.getArtistId(), a);
		for(Adiacenza a:dao.getAdiacenze(role, idMap)) {
			Graphs.addEdge(this.grafo,a.getA1() , a.getA2(), a.getOpereComune());
		}
		return "Grafo creato:\n#Vertici: "+this.grafo.vertexSet().size()+"\n#Archi: "
		+this.grafo.edgeSet().size()+"\n";
	}
	
	public List<Adiacenza> getArtistiConnessi(String role){
		List<Adiacenza> result= dao.getAdiacenze(role, idMap);
		Collections.sort(result);
		return result;
	}
	public List<String> getListRole(){
		return dao.listRole();
	}

	public boolean artistaValido(int artistId) {
		if(this.idMap.containsKey(artistId))
			return true;
		else 
			return false;
	}
	
	public List<Artist> calcolaPercorso(int artistId){
		best=new ArrayList<>();
		List<Artist> parziale=new ArrayList<>();
		
		parziale.add(idMap.get(artistId));
		ricorsione(parziale,-100); //-100 al posto del peso (peso degli archi)
		
		return best;
	}

	private void ricorsione(List<Artist> parziale, int peso) {
		Artist lastArtist=parziale.get(parziale.size()-1);
		List<Artist> vicini=Graphs.neighborListOf(this.grafo, lastArtist);
		for(Artist a: vicini ) {
			if(!parziale.contains(a) && peso==-100) {
				//prima iterazione
				parziale.add(a);
				ricorsione(parziale,(int)this.grafo.getEdgeWeight(this.grafo.getEdge(lastArtist, a)));
				parziale.remove(parziale.size()-1);
			}else {
				if(!parziale.contains(a) && this.grafo.getEdgeWeight(this.grafo.getEdge(lastArtist, a))==peso) {
					//Iterazioni dopo la prima con peso arco corretto
					parziale.add(a);
					ricorsione(parziale,peso);
					parziale.remove(parziale.size()-1);
					}
			}
		}
		
		//Condizione di terminazione
		if(parziale.size()>best.size()) {
			this.best=new ArrayList<>(parziale);
		}
		
	}
	
}
