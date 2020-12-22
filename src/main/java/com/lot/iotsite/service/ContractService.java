package com.lot.iotsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.domain.Contract;
import com.lot.iotsite.dto.ContractDto;
import com.lot.iotsite.dto.ContractsDto;
import com.lot.iotsite.dto.SimpleContractDto;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ContractService {

     /**
      * 新建合同
      * @param contract
      * @return
      */
     Boolean addContract(Contract contract);

     /**
      * 更改合同信息
      * @param contract
      * @return
      */
     Boolean updateContract(Contract contract);

     /**
      * 删除合同
      * @param id
      * @return
      */
     Boolean deleteContractById(Long id);

     /**
      * 获取所有合同
      * @param name
      * @param type
      * @param startTime
      * @param endTime
      * @param page
      * @return
      */
     IPage<ContractsDto> getAllContractsDto( String name, String type, String startTime, String endTime, Integer status, IPage page);

     /**
      * 获取单个合同
      * @param id
      * @return
      */
     ContractDto getContract(Long id);

     Contract getContractById(Long id);

     List<SimpleContractDto> getAllContractName(String clientName);
}
