package cn.anlper.train.mapper;

import cn.anlper.train.entities.Train;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace
public interface TrainMapper extends Mapper<Train> {
}