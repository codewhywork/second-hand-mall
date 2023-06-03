package com.bistu.why.product.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bistu.why.model.dto.SpuSavaDto;
import com.bistu.why.model.stock.StockEntity;
import com.bistu.why.service.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.bistu.why.model.product.
        SpuInfoEntity;
import com.bistu.why.service.product.SpuInfoService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * spu信息
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@RestController
@RequestMapping("product/spuinfo")

public class SpuInfoController {
    @Value("${file.upload}")
    public String path;

    @Resource
    StockService stockService;


    @Autowired
    private SpuInfoService spuInfoService;

    @GetMapping("/stock/{skuId}")
    public StockEntity getStock(@PathVariable("skuId") Long skuId) {
        StockEntity stockEntity = stockService.getOne(new LambdaQueryWrapper<StockEntity>().eq(StockEntity::getSkuId, skuId));
        return stockEntity;
    }

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        if (ObjectUtils.isEmpty(file)) {
            System.out.println("文件是空的");
            return R.error();
        }
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());

        String dir = applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + path;
        String oldName = file.getOriginalFilename();
        String suffix = oldName.substring(oldName.lastIndexOf(".") + 1);
        String newName = UUID.randomUUID() + "." + suffix;
        File descFile = new File(dir, newName);
        if (!descFile.exists()) {
            descFile.createNewFile();
        }
        try {
            file.transferTo(descFile);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        System.out.println("success");
        return R.ok();
    }

    /**
     * 添加商品
     */
    @PostMapping("/save")
    public R save(@RequestBody List<SpuSavaDto> spuSavaDto) {
        spuInfoService.save(spuSavaDto);
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @Cacheable(key = "#params.getOrDefault('page',0) + ':' +#params.getOrDefault('limit',10)", cacheNames = "spuInfoList")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);
        return R.ok().put("spuInfo", spuInfo);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
