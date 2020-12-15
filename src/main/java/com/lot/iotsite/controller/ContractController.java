package com.lot.iotsite.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.constant.Progress;
import com.lot.iotsite.domain.Contract;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.ContractDto;
import com.lot.iotsite.dto.ContractsDto;
import com.lot.iotsite.dto.SimpleContractDto;
import com.lot.iotsite.queryParam.ContractParam;
import com.lot.iotsite.service.ContractService;
import com.lot.iotsite.utils.AccountUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {

   @Autowired
    private ContractService contractService;

    /**
     * 新建合同
     * @param contractParam
     * @return
     */
   @GetMapping("/contract")
   public Boolean addContract(@SpringQueryMap ContractParam contractParam){
       //TODO 校验参数
       Contract contract=new Contract();
       BeanUtils.copyProperties(contractParam,contract);
       contract.setProgress(Progress.CREATED.code());
       //TODO 获取登录用户id
       return contractService.addContract(contract);
   }

    /**
     * 更改合同
     * @param contractParam
     * @return
     */
   @PutMapping("/contract")
   public Boolean updateContract(@SpringQueryMap ContractParam contractParam){
       //TODO 校验参数
       Contract contract=new Contract();
       Contract contract1=contractService.getContractById(contract.getId());
       BeanUtils.copyProperties(contractParam,contract);
       contract.setProgress(contract1.getProgress());
       contract.setCreaterId(contract1.getCreaterId());
       return contractService.updateContract(contract);
   }

    /**
     * 删除合同
     * @param id
     * @return
     */
   @DeleteMapping("/contract/{id}")
   public Boolean deleteContract(@PathVariable("id") Long id){
       return contractService.deleteContractById(id);
   }

    /**
     * 获取所有合同
     * @param name
     * @param type
     * @param startTime
     * @param endTime
     * @param status
     * @param current
     * @return
     */
   @GetMapping("/contracts")
   public IPage<ContractsDto> getContracts(@RequestParam("name") String name, @RequestParam("type")String type,
                                           @RequestParam("startTime")String startTime, @RequestParam("endTime")String endTime,@RequestParam("status")Integer status,
                                           @RequestParam("page.current")Long current){
       //TODO 校验参数
       Page page=new Page<>();
       page.setCurrent(current);
       page.setSize(10);
       return contractService.getAllContractsDto(name,type,startTime,endTime,status,page);
   }

    /**
     * 获取单个合同信息
     * @param id
     * @return
     */
   @GetMapping("/contract/{id}")
   public ContractDto getContract(@PathVariable("id") Long id){
       return contractService.getContract(id);
    }

   @GetMapping("/client_names")
   public List<SimpleContractDto> getAllContractName(){
       return  contractService.getAllContractName();
   }


}
