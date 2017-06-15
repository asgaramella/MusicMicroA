package it.polito.tdp.music.db;

import it.polito.tdp.music.model.Artist;
import it.polito.tdp.music.model.ArtistAndNum;
import it.polito.tdp.music.model.City;
import it.polito.tdp.music.model.Country;
import it.polito.tdp.music.model.Listening;
import it.polito.tdp.music.model.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MusicDAO {
	
	public List<Country> getAllCountries() {
		final String sql = "SELECT id, country FROM country" ;
		
		List<Country> countries = new LinkedList<Country>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				countries.add( new Country(res.getInt("id"), res.getString("country"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return countries ;
		
	}
	
	public List<City> getAllCities() {
		final String sql = "SELECT id, city FROM city" ;
		
		List<City> cities = new LinkedList<City>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				cities.add( new City(res.getInt("id"), res.getString("city"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return cities ;
		
	}

	
	public List<Artist> getAllArtists() {
		final String sql = "SELECT id, artist FROM artist" ;
		
		List<Artist> artists = new LinkedList<Artist>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				artists.add( new Artist(res.getInt("id"), res.getString("artist"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return artists ;
		
	}

	public List<Track> getAllTracks() {
		final String sql = "SELECT id, track FROM track" ;
		
		List<Track> tracks = new LinkedList<Track>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				tracks.add( new Track(res.getInt("id"), res.getString("track"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return tracks ;
		
	}
	
	public List<Listening> getAllListenings() {
		final String sql = "SELECT id, userid, month, weekday, longitude, latitude, countryid, cityid, artistid, trackid FROM listening" ;
		
		List<Listening> listenings = new LinkedList<Listening>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				listenings.add( new Listening(res.getLong("id"), res.getLong("userid"), res.getInt("month"), res.getInt("weekday"),
						res.getDouble("longitude"), res.getDouble("latitude"), res.getInt("countryid"), res.getInt("cityid"),
						res.getInt("artistid"), res.getInt("trackid"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return listenings ;
		
	}

	public List<Month> getMesi() {
		final String sql = "SELECT DISTINCT listening.month " + 
		"FROM listening " + 
		"ORDER BY listening.month ";
		
		
		List<Month> list = new LinkedList<Month>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				list.add(Month.of(res.getInt("listening.month")));
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return list ;
	}
	
	public List<ArtistAndNum> getClassificaforMese(int mese) {
		final String sql = "SELECT listening.artistid,artist.artist, COUNT(*) as ascolti " + 
				"FROM listening, artist " + 
				"WHERE listening.month=? " + 
				"AND listening.artistid=artist.id " + 
				"GROUP BY listening.artistid " + 
				"ORDER BY ascolti DESC";
		
		
		List<ArtistAndNum> list = new LinkedList<ArtistAndNum>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, mese);
			
			ResultSet res = st.executeQuery() ;
			boolean flag=true;
			int counter=1;
			while( res.next() && flag==true){
				list.add(new ArtistAndNum(new Artist(res.getInt("listening.artistid"),res.getString("artist.artist")),res.getInt("ascolti")));
				counter++;
				if(counter==21)
					flag=false;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return list ;
	}
	
	public List<Country> getCountryForArtist(int mese, Artist a) {
		final String sql = "SELECT DISTINCT country.* " + 
				"FROM listening,country " + 
				"WHERE listening.artistid=? " + 
				"AND listening.month=? " + 
				"AND listening.countryid=country.id";
		
		
		List<Country> list = new LinkedList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, a.getId());
			st.setInt(2, mese);
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ){
				list.add(new Country(res.getInt("country.id"),res.getString("country.country")));
				
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return list ;
		
	}
	
	
	public Integer getArtistiForCountries(Country c1, Country c2, int mese) {
		final String sql = "SELECT * " + 
				"FROM listening as l1, listening as l2 " + 
				"WHERE l1.countryid=? " + 
				"AND  l2.countryid=? " + 
				"AND l1.month=? " + 
				"AND l2.month=? " + 
				"AND l1.artistid=l2.artistid " + 
				"GROUP BY l1.artistid";
		
		
		int counter=0;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, c1.getId());
			st.setInt(2, c2.getId());
			st.setInt(3,mese);
			st.setInt(4, mese);
			
			ResultSet res = st.executeQuery() ;
			
			
			while( res.next() ){
				counter++;
				
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return counter ;
		
		
		
	}
	
	public static void main(String[] args) {
		MusicDAO dao = new MusicDAO() ;
		
		List<Country> countries = dao.getAllCountries() ;
		//System.out.println(countries) ;
		
		List<City> cities = dao.getAllCities() ;
		//System.out.println(cities) ;
		
		List<Artist> artists = dao.getAllArtists() ;
		
		List<Track> tracks = dao.getAllTracks() ;
		
		List<Listening> listenings = dao.getAllListenings() ;



		System.out.format("Loaded %d countries, %d cities, %d artists, %d tracks, %d listenings\n", 
				countries.size(), cities.size(), artists.size(), tracks.size(), listenings.size()) ;
	}



	

	

}
