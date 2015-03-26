package com.yazuo.erp.api.dao.impl;

import com.google.common.base.Joiner;
import com.yazuo.erp.api.dao.RenewCardTypeDao;
import com.yazuo.erp.api.vo.CardTypeVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 */

/**
 *
 * select * from card_batch where cardtype_id =6 order by create_date desc limit 1;
 * <p/>
 * select count(1) from card_record where batch_id = 514 and activation_flag=false;
 * <p/>
 */
@Repository
public class RenewCardtypeDaoImpl implements RenewCardTypeDao {
    @Resource
    private JdbcTemplate jdbcTemplateCrm210;

    @Resource
    private JdbcTemplate jdbcTemplateCrm;

    @Override
    public List<Integer> getAllCardtypeIds(List<Integer> brandIds) {
        StringBuffer sql = new StringBuffer("SELECT id FROM trade.cardtype");
        if (CollectionUtils.isNotEmpty(brandIds)) {
            sql.append(" WHERE merchant_id in (" +
                    " WITH RECURSIVE r AS ( " +
                    " SELECT * FROM trade.merchant as tree WHERE merchant_id in (" + Joiner.on(",").join(brandIds) + ")" +
                    " UNION ALL" +
                    " SELECT tree.* FROM trade.merchant as tree, r WHERE tree.parent = r.merchant_id ) " +
                    " SELECT merchant_id FROM r WHERE (status = 1 or status = 3) ORDER BY merchant_id )"
            );
        }
        return this.jdbcTemplateCrm210.query(sql.toString(), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("id");
            }
        });
    }

    @Override
    public CardTypeVO getCardType(Integer cardTypeId) {
        String sql = "SELECT * FROM trade.cardtype WHERE id=?";
        List<CardTypeVO> cardTypeVOs = this.jdbcTemplateCrm210.query(sql, new RowMapper<CardTypeVO>() {
            @Override
            public CardTypeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CardTypeVO cardTypeVO = new CardTypeVO();
                cardTypeVO.setCardTypeId(rs.getInt("id"));
                cardTypeVO.setCardType(rs.getString("cardtype"));
                cardTypeVO.setStatus(rs.getInt("status"));
                return cardTypeVO;
            }
        }, cardTypeId);
        CardTypeVO cardTypeVO = cardTypeVOs.size() > 0 ? cardTypeVOs.get(0) : null;
        if (cardTypeVO != null) {
            CardTypeVO cardBatchInfo = this.getCardBatch(cardTypeVO.getCardTypeId());
            if (cardBatchInfo != null) {
                BeanUtils.copyProperties(cardBatchInfo, cardTypeVO, new String[]{"cardTypeId", "cardType","status"});
            }
        }
        return cardTypeVO;
    }

    public CardTypeVO getCardBatch(Integer cardTypeId) {
        String cardBatchSql = "SELECT tc.*,tm.brand_id FROM trade.card_batch tc left join trade.merchant tm on tc.merchant_id = tm.merchant_id WHERE tc.cardtype_id = ? ORDER BY tc.create_date DESC LIMIT 1";
        List<CardTypeVO> cardTypeVOs = this.jdbcTemplateCrm210.query(cardBatchSql, new RowMapper<CardTypeVO>() {
            @Override
            public CardTypeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CardTypeVO cardTypeVO = new CardTypeVO();
                cardTypeVO.setCreatedDate(rs.getDate("create_date"));
                cardTypeVO.setBatchId(rs.getInt("batch_id"));
                cardTypeVO.setBrandId(rs.getInt("brand_id"));
                cardTypeVO.setEntityCard(rs.getInt("card_batch") == 3);//3 表示正式卡即实体卡
                cardTypeVO.setTotalNum(rs.getInt("quantity"));
                return cardTypeVO;
            }
        }, cardTypeId);
        return cardTypeVOs.size() > 0 ? cardTypeVOs.get(0) : null;
    }

    @Override
    public Integer getNoActivatedNum(Integer cardBatchId) {
        String sql = "SELECT count(1) FROM trade.card_record WHERE batch_id = ? AND activation_flag=FALSE";
        return this.jdbcTemplateCrm210.queryForInt(sql, cardBatchId);
    }

    @Override
    public Integer getNewCardNum(Integer brandId, Date from, Date to) {
        String sql = "SELECT sum(new_card) FROM report.daily_merchant_summary WHERE brand_id=? AND trans_date >= ? AND trans_date < ? ";
        return this.jdbcTemplateCrm.queryForInt(sql, brandId, from, to);
    }
}
