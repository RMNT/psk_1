package mybatis.dao;


import mybatis.model.Event;
import org.mybatis.cdi.Mapper;

import java.util.List;


@Mapper
public interface EventMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.EVENT
     *
     * @mbg.generated Sun Mar 03 13:45:02 EET 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.EVENT
     *
     * @mbg.generated Sun Mar 03 13:45:02 EET 2019
     */
    int insert(Event record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.EVENT
     *
     * @mbg.generated Sun Mar 03 13:45:02 EET 2019
     */
    Event selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.EVENT
     *
     * @mbg.generated Sun Mar 03 13:45:02 EET 2019
     */
    List<Event> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.EVENT
     *
     * @mbg.generated Sun Mar 03 13:45:02 EET 2019
     */
    int updateByPrimaryKey(Event record);
}
