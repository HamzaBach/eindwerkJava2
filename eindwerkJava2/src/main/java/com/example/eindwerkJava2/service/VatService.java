package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Vat;
import com.example.eindwerkJava2.repositories.VatRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VatService {
    @Autowired
    private final VatRepository vatRepository;

    public VatService(VatRepository vatRepository){
        this.vatRepository=vatRepository;
    }

    public SuccessEvaluator<Vat> save(Vat vat){
        SuccessEvaluator<Vat> isSaveSuccess = new SuccessEvaluator<>();
        if(vatRepository.existsVatByVatRate(vat.getVatRate())){
            isSaveSuccess.setMessage("There already exists a vatrate for: "+vat.getVatRate()*100+"%");
            isSaveSuccess.setIsSuccessfull(false);
        }else{
            vatRepository.save(vat);
            isSaveSuccess.setMessage("Vatrate of"+vat.getVatRate()*100+"% has been succesfully saved.");
            isSaveSuccess.setIsSuccessfull(true);
        }
        return isSaveSuccess;
    }

    public List<Vat> getAllActiveVats(){
        return vatRepository.findByActive(1);
    }

    public boolean existsVatrate(Vat vat){
        Optional<Vat> duplicateVat = vatRepository.findByVatRate(vat.getVatRate());
        if (vatRepository.findByVatRate(vat.getVatRate()).isEmpty()){
            return false;
        } else {
            return duplicateVat.get().getActive() == 1;
        }
    }

}
