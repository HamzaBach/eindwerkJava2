package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Vat;
import com.example.eindwerkJava2.repositories.VatRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class VatService {
    @Autowired
    private final VatRepository vatRepository;

    public VatService(VatRepository vatRepository){
        this.vatRepository=vatRepository;
    }
    private static final DecimalFormat df = new DecimalFormat("0.00");  // rounding a double to 2 decimal points

    public SuccessEvaluator<Vat> save(Vat vat){

        SuccessEvaluator<Vat> isSaveSuccess = new SuccessEvaluator<>();
        if(vatRepository.existsVatByVatRate(vat.getVatRate())){
            isSaveSuccess.setMessage("There already exists a VAT rate for: "+df.format(vat.getVatRate()*100)+"%");
            isSaveSuccess.setIsSuccessfull(false);
        }else{
            vatRepository.save(vat);
            isSaveSuccess.setMessage("VAT rate of"+df.format(vat.getVatRate()*100)+" % has been succesfully saved.");
            isSaveSuccess.setIsSuccessfull(true);
        }
        return isSaveSuccess;
    }

    public SuccessEvaluator<Vat> getAllActiveVats(){
        SuccessEvaluator<Vat> getActiveVatRates = new SuccessEvaluator<>();
        if(vatRepository.findByActive(1).size()>0){
            getActiveVatRates.setIsSuccessfull(true);
            getActiveVatRates.setEntities(vatRepository.findByActive(1));
        }else {
            getActiveVatRates.setIsSuccessfull(false);
            getActiveVatRates.setMessage("No VAT rates found within the database.");
        }
        return getActiveVatRates;
    }

    public boolean existsVatrate(Vat vat){
        Optional<Vat> duplicateVat = vatRepository.findByVatRate(vat.getVatRate());
        if (vatRepository.findByVatRate(vat.getVatRate()).isEmpty()){
            return false;
        } else {
            return duplicateVat.get().getActive() == 1;
        }
    }

    public SuccessEvaluator<Vat> findById(long id){
        SuccessEvaluator<Vat> success = new SuccessEvaluator<>();
        if(vatRepository.findById(id).isEmpty()){
            success.setIsSuccessfull(false);
            success.setMessage("VAT rate not found!");
        } else {
            Vat retrievedVatRate = vatRepository.findById(id).get();
            success.setEntity(retrievedVatRate);
            success.setIsSuccessfull(true);
        }
        return success;
    }

    public SuccessObject deleteVatRate(Vat vat) {
        SuccessObject success = new SuccessEvaluator<>();
        vat.setActive(0);
        this.vatRepository.save(vat);
        if(vatRepository.findById(vat.getVatId()).get().getActive()==0){
            success.setIsSuccessfull(true);
            success.setMessage("VAT rate "+ df.format(vat.getVatRate())+" (ID: "+vat.getVatId()+" ) was successfully removed");
        } else{
            success.setIsSuccessfull(false);
            success.setMessage("VAT rate " + df.format(vat.getVatRate())+" (ID: "+vat.getVatId()+" )" + " could not be removed.");
        }
        return success;
    }

}
