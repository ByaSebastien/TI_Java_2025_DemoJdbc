package be.bstorm.repositories.impls;

import be.bstorm.repositories.BaseRepository;
import be.bstorm.utils.ConnectionUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseRepositoryImpl<TEntity, TId> implements BaseRepository<TEntity, TId> {

    private final String tableName;
    private final String columnIdName;

    protected BaseRepositoryImpl(String tableName, String columnIdName) {
        this.tableName = tableName;
        this.columnIdName = columnIdName;
    }

    @Override
    public List<TEntity> findAll() {

        try (Connection conn = ConnectionUtils.getConnection()) {

            String query = "SELECT * FROM " + tableName;

            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            List<TEntity> entities = new ArrayList<>();

            while (rs.next()) {
                entities.add(buildEntity(rs));
            }

            return entities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TEntity findById(TId id) {

        try(Connection conn = ConnectionUtils.getConnection()) {

            String query = "SELECT * FROM " + tableName + " WHERE " + columnIdName + " = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                throw new NoSuchElementException();
            }

            return buildEntity(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TId save(TEntity entity){

        try (Connection conn = ConnectionUtils.getConnection()) {

            Field[] fields = entity.getClass().getDeclaredFields();
            StringBuilder columns = new StringBuilder();
            StringBuilder params = new StringBuilder();

            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getName().equalsIgnoreCase(columnIdName)) {
                    continue;
                }
                columns.append(field.getName()).append(",");
                params.append("?,");
            }

            columns.deleteCharAt(columns.length() - 1);
            params.deleteCharAt(params.length() - 1);

            String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + params + ")";

            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            Integer index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getName().equalsIgnoreCase(columnIdName)) {
                    continue;
                }
                ps.setObject(index++, field.get(entity));
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if(!rs.next()) {
                throw new RuntimeException("Failed to save entity");
            }

            return (TId) rs.getObject(columnIdName);

        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(TId id, TEntity entity){
        try (Connection conn = ConnectionUtils.getConnection()) {

            Field[] fields = entity.getClass().getDeclaredFields();
            StringBuilder updateClause = new StringBuilder();

            for (Field field : fields) {
                field.setAccessible(true);
                if ((field.getName().equalsIgnoreCase(columnIdName))) {
                    updateClause.append(field.getName()).append(" = ?,");
                }
            }

            updateClause.deleteCharAt(updateClause.length() - 1);

            String query = "update " + tableName + " set " + updateClause + " where " + columnIdName + " = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            Integer index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getName().equalsIgnoreCase(columnIdName)) {
                    continue;
                }
                ps.setObject(index++, field.get(entity));
            }
            ps.setObject( fields.length + 1, id);

            return ps.executeUpdate() == 1;

        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(TId id) {

        try(Connection conn = ConnectionUtils.getConnection()) {

            String query = "DELETE FROM " + tableName + " WHERE " + columnIdName + " = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setObject(1, id);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract TEntity buildEntity(ResultSet rs) throws SQLException;
}
