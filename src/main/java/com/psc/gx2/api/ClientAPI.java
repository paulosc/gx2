package com.psc.gx2.api;

import com.psc.gx2.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "clientAPI", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ClientAPI {
    @GetMapping("/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
    List<Client> getClients();
}
