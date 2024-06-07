package com.firstjobapp.company.impl;

import com.firstjobapp.company.Company;
import com.firstjobapp.company.CompanyRepository;
import com.firstjobapp.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> companyOptional=companyRepository.findById(id);
        if(companyOptional.isPresent()){
            Company companyToUpdate=companyOptional.get();
            companyToUpdate.setName(company.getName());
            companyToUpdate.setDescription(company.getDescription());
            companyToUpdate.setJobs(company.getJobs());

            companyRepository.save(company);
            return true;
        }else {

            return false;
        }
    }

    @Override
    public boolean deleteCompanyById(Long id) {

        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
