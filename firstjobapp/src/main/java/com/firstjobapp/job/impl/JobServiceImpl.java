package com.firstjobapp.job.impl;

import com.firstjobapp.job.Job;
import com.firstjobapp.job.JobRepository;
import com.firstjobapp.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;
//    private List<Job> jobs=new ArrayList<>();
//    private Long nextId=1L;
    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
       return jobRepository.findById(id).orElse(null);

    }

    @Override
    public boolean deleteById(Long id) {
       try {
           jobRepository.deleteById(id);

           return true;
       }
       catch (Exception e){
           return false;
       }

    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional=jobRepository.findById(id);

        if(jobOptional.isPresent()){
            Job job=jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;


        }

        return false;

    }



}
