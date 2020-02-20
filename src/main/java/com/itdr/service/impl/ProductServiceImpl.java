package com.itdr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.CategoryMapper;
import com.itdr.mapper.ProductMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.service.ProductService;
import com.itdr.utils.ObjectToVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    //获取商品直接分类
    @Override
    public ServerResponse<Category> baseCategory(Integer pid) {
        //参数合法判断
        if (pid == null || pid < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getCode(),
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getDesc());
        }
        //根据父id查找直接子类
        List<Category> li = categoryMapper.selectByParentID(pid);

        //返回成功数据
        return ServerResponse.successRS(li);
    }
    //获取商品详情
    @Override
    public ServerResponse<ProductVO> detail(Integer productId) {
        //参数合法判断
        if (productId == null || productId < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getCode(),
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getDesc());
        }
        //根据父id查找直接子类
        Product product= productMapper.selectByPrimaryKey(productId);
        if (product == null || product.getStatus() != 1){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getCode(),
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getDesc());
        }

        //封装VO
        ProductVO productVO = ObjectToVOUtil.ProductToUserVO(product);

        //返回成功数据
        return ServerResponse.successRS(productVO);
    }
    //商品模糊查询
    @Override
    public ServerResponse<ProductVO> list(String word,Integer pageNum,Integer pageSize,
                                          String orderBy) {
        //参数合法判断
        if (StringUtils.isEmpty(word)){
            return ServerResponse.defeatedRS(
                    ConstCode.DEFAULT_FAIL,
                    "参数不能为空");
        }
        //排序参数处理
        String[] split = new String[2];
        if (!StringUtils.isEmpty(orderBy)){
            split = orderBy.split("_");
        }
        //模糊查询数据
        String keyWord = "%"+word+"%";
        //开启分页
        PageHelper.startPage(pageNum,pageSize,split[0]+" "+split[1]);
        List<Product> li = productMapper.selectByName(keyWord);
        PageInfo pageInfo = new PageInfo(li);
        //封装VO
        List<ProductVO> liNew = new ArrayList<ProductVO>();
        for (Product product : li) {
            ProductVO pvo = ObjectToVOUtil.ProductToUserVO(product);
            liNew.add(pvo);
        }
        pageInfo.setList(liNew);

        //返回成功数据
        return ServerResponse.successRS(pageInfo);
    }

}
