package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import domain.BoardVO;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {
	private SqlSession sql;
	private final String NS = "BoardMapper.";
	
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(BoardVO bvo) {
		int isUp = sql.insert(NS+"add", bvo);
		if(isUp > 0) {
			sql.commit();
		}
		return isUp;
	}

	@Override
	public List<BoardVO> selectList() {
		return sql.selectList(NS+"list");
	}

	@Override
	public BoardVO selectDetail(int bno) {
		return sql.selectOne(NS+"detail", bno);
	}

	@Override
	public int update(BoardVO bvo) {
		int isUp = sql.update(NS+"mod", bvo);
		if(isUp > 0) {
			sql.commit();
		}
		return isUp;
	}

	@Override
	public int delete(int bno) {
		return sql.delete(NS+"del", bno);
	}

}
