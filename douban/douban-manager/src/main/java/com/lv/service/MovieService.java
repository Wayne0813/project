package com.lv.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.lv.dao.MovieDao;
import com.lv.dao.MovieTypeDao;
import com.lv.dao.ReplyDao;
import com.lv.dao.TypeDao;
import com.lv.entity.Movie;
import com.lv.entity.MovieType;
import com.lv.entity.Type;
import com.lv.exception.ServiceException;
import com.lv.util.Page;

public class MovieService {
	private TypeDao typeDao = new TypeDao();
	private ReplyDao replyDao = new ReplyDao();
	private MovieDao movieDao = new MovieDao();
	private MovieTypeDao movieTypeDao = new MovieTypeDao();
	
	public void addFilm(String filmName, String director, String area, String year, String imgPath, String content,
			String[] types) {

		Movie movie = new Movie();
		movie.setFilmName(filmName);
		movie.setFilmDirector(director);
		movie.setArea(area);
		movie.setYear(year);
		movie.setImgPath(imgPath);
		movie.setContent(content);

		String simpoContent = Jsoup.parseBodyFragment(content).toString();
		/*String simpoContent = doc.select("p").first().toString();*/
		simpoContent = Jsoup.clean(simpoContent, Whitelist.none());
		
		if(simpoContent.length() > 100) {
			simpoContent = simpoContent.substring(0, 100) + "......";
		}
		movie.setSimpoContent(simpoContent);
		
		int id  = movieDao.save(movie);
		
		MovieType movieType = new MovieType();
		
		for(String type : types) {
			movieType.setMovieId(id);
			movieType.setTypeId(Integer.valueOf(type));
			movieTypeDao.save(movieType);
		}
		
	}

	/**
	 * 验证输入的电影名是都已经存在，如果不存在，返回true(可用);如果存在，判断时新增还是修改，如果是新增
	 * 		返回false(不可用),如果是修改，判断时和自己重复还是和别的电影重复，如果和自己重复,返回true(可用),如果和别的电影重复，则不可用
	 * @param movieName
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean findMovieByName(String movieName, String id) throws UnsupportedEncodingException {
		
		Movie movie = movieDao.queryMovieByName(movieName);
		
		if(movie == null) {
			return true;
		}
		if(StringUtils.isNotEmpty(id) && movie.getId() == Integer.valueOf(id)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Movie> findMovieList(Integer start, Integer pageSize) {
		List<Movie> movieList = movieDao.queryAllMovie(start, pageSize);
		for(Movie movie : movieList) {
			TypeDao typeDao = new TypeDao();
			List<Type> typeList = typeDao.queryByMovieId(movie.getId());
			movie.setTypeList(typeList);
		}
		return movieList;
	}

	/**
	 * 删除电影，删除电影信息，电影对应的评论信息，电影类型关系表中与该电影有关的信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Movie deleteMovie(String id) throws ServiceException {
		if(StringUtils.isEmpty(id)) {
			throw new ServiceException("参数异常!");
		}
		if(!StringUtils.isNumeric(id)) {
			throw new ServiceException("参数异常!");
		}
		Movie movie = movieDao.queryMovieById(Integer.valueOf(id));
		movieDao.deleteMovie(Integer.valueOf(id));
		replyDao.deleteReplyByMovieId(Integer.valueOf(id));
		movieTypeDao.deleteMovieType(Integer.valueOf(id));
		return movie;
	}

	/**
	 * 通过ID找到对应的电影信息，并return
	 * @param id
	 * @return
	 */
	public Movie findMovieById(String id) {
		if(StringUtils.isEmpty(id)) {
			throw new ServiceException("参数异常!");
		}
		if(!StringUtils.isNumeric(id)) {
			throw new ServiceException("参数异常!");
		}
		
		Movie movie = movieDao.queryMovieById(Integer.valueOf(id));
		List<Type> typeList = typeDao.queryByMovieId(Integer.valueOf(id));
		movie.setTypeList(typeList);
		return movie;
	}

	public Page<Movie> findMovieByPage(String p, String keys) {
		
		p = StringUtils.isEmpty(p) ? "1" : p;
		p = StringUtils.isNumeric(p) ? p : "1";
		int count = movieDao.count(keys);
		Page<Movie> page = new Page<>(Integer.valueOf(p), count);
		
		Map<String, String> params = new HashMap<>();
		params.put("start", page.getStart().toString());
		params.put("pageSize", page.getPageSize().toString());
		params.put("keys", keys);
		
		List<Movie> movieList = movieDao.queryMovieByParams(params);
		for(Movie movie : movieList) {
			List<Type> typeList = typeDao.queryByMovieId(movie.getId());
			movie.setTypeList(typeList);
		}
		page.setTList(movieList);
		
		return page;
	}

	public void editMovie(String id, String filmName, String director, String area, String year, String imgPath, String content,
			String[] types) throws ServiceException {

		Integer movieId = Integer.valueOf(id);
		Movie movie = movieDao.queryMovieById(movieId);
		
		if(movie != null) {
			movie.setId(movieId);
			movie.setFilmName(filmName);
			movie.setFilmDirector(director);
			movie.setArea(area);
			movie.setYear(year);
			movie.setImgPath(imgPath);
			movie.setContent(content);
			
			String simpoContent = Jsoup.parseBodyFragment(content).toString();
			/*String simpoContent = doc.select("p").first().toString();*/
			simpoContent = Jsoup.clean(simpoContent, Whitelist.none());
			
			if(simpoContent.length() > 100) {
				simpoContent = simpoContent.substring(0, 100) + "......";
			}
			movie.setSimpoContent(simpoContent);
			
			movieTypeDao.deleteMovieType(movieId);
			MovieType movieType = new MovieType();
			for(String type : types) {
				movieType.setMovieId(movieId);
				movieType.setTypeId(Integer.valueOf(type));
				movieTypeDao.save(movieType);
			}
			
			movieDao.update(movie);
		} else {
			throw new ServiceException("该资源不存在或已被删除,无法进行修改操作!");
		}
		
	}


}
