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
import io.swagger.models.auth.In;
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
   @PostMapping("/contract")
   public Boolean addContract(@SpringQueryMap ContractParam contractParam,HttpServletRequest request){
       //TODO 校验参数
       Contract contract=new Contract();
       BeanUtils.copyProperties(contractParam,contract);
       contract.setProgress(Progress.CREATED.code());
       //获取登录用户id
       Long createrId=AccountUtils.getCurrentUser(request);
       contract.setCreaterId(createrId);
       return contractService.addContract(contract);
   }

    /**
     * 更改合同
     * @param contractParam
     * @return
     */
   @PutMapping("/contract/{id}")
   public Boolean updateContract(@SpringQueryMap ContractParam contractParam,@PathVariable("id") Long id){
       //TODO 校验参数
       Contract contract=new Contract();
       Contract contract1=contractService.getContractById(id);
       BeanUtils.copyProperties(contractParam,contract);
       contract.setProgress(contract1.getProgress());
       contract.setCreaterId(contract1.getCreaterId());
       contract.setId(id);
       return contractService.updateContract(contract);
   }

    /**
     * 删除合同
     * @param id
     * @return
     */
   @DeleteMapping("/{id}")
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
   public IPage<ContractsDto> getContracts(@RequestParam(value = "name",required = false) String name, @RequestParam(value = "type",required = false)String type,
                                           @RequestParam(value = "startTime",required = false)String startTime, @RequestParam(value = "endTime",required = false)String endTime,
                                           @RequestParam(value = "status",required = false)Integer status, @RequestParam("page.current")Long current){
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
   @GetMapping("/{id}")
   public ContractDto getContract(@PathVariable("id") Long id){
       return contractService.getContract(id);
    }

   @GetMapping("/client")
   public List<SimpleContractDto> getAllContractName(@RequestParam("name")String clientName){
       return  contractService.getAllContractName(clientName);
   }

}
