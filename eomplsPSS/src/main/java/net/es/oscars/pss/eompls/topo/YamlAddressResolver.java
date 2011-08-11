package net.es.oscars.pss.eompls.topo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.net.InetAddress;
import java.net.UnknownHostException;


import net.es.oscars.utils.config.ConfigException;
import net.es.oscars.utils.config.ConfigHelper;
import net.es.oscars.utils.topology.NMWGParserUtil;

public class YamlAddressResolver {
    @SuppressWarnings("unchecked")
    private Map config;
    private HashMap<String, InetAddress> ipAddresses;
    private HashMap<String, String> nodeVendors;
    private static YamlAddressResolver instance;

    private YamlAddressResolver() {
        ipAddresses = new HashMap<String, InetAddress>();
        nodeVendors = new HashMap<String, String>();
    }

    public static YamlAddressResolver getInstance() {
        if (instance == null) {
            instance = new YamlAddressResolver();
        }
        return instance;
    }

    public String getVendor(String urn) {
        urn = NMWGParserUtil.normalizeURN(urn);
        return nodeVendors.get(urn);
    }

    public InetAddress getInetAddress(String urn) {
        urn = NMWGParserUtil.normalizeURN(urn);
        return ipAddresses.get(urn);
    }


    @SuppressWarnings("unchecked")
    public void loadConfig(String filePath) throws ConfigException {
        config = ConfigHelper.getConfiguration(filePath);
        assert config != null : "No configuration";
        Set<String> urns = (Set<String>) config.keySet();
        for (String urn : urns) {
            String normalUrn = NMWGParserUtil.normalizeURN(urn);
            Map<String, String> urnConfig = (Map<String, String>) config.get(urn);
            if (urnConfig.containsKey("ipv4")) {
                String ipAddress = urnConfig.get("ipv4");
                if (ipAddress != null && ipAddress.length() > 0) {
                    try {
                        InetAddress address = InetAddress.getByName(ipAddress);
                        ipAddresses.put(urn, address);
                    } catch (UnknownHostException e) {
                        throw new ConfigException(e.getMessage());
                    }
                } else {
                    throw new ConfigException("No ipv4 address configured for urn: "+urn);
                }
            }

            if (NMWGParserUtil.getURNType(normalUrn) == NMWGParserUtil.NODE_TYPE) {
                if (urnConfig.containsKey("vendor")) {
                    String vendor = urnConfig.get("vendor");
                    if (vendor != null && vendor.length() > 0) {
                        nodeVendors.put(urn, vendor);
                    } else {
                        throw new ConfigException("node without vendor: "+urn);
                    }
                } else {
                    throw new ConfigException("node without vendor: "+urn);
                }
            }
        }
    }

}
