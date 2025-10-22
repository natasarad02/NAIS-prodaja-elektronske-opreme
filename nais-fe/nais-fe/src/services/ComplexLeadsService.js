const BASE_URL = 'http://localhost:8081/api/complex';

const executeGet = async (endpoint) => {
    try {
        const response = await fetch(endpoint);

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`API Error: ${response.status} (${response.statusText}) - ${endpoint}`, errorText);
            throw new Error(`Greška pri pozivu API-ja: ${response.statusText}`);
        }

        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            return await response.json();
        }
        
        return null;

    } catch (error) {
        console.error("Fetch operation failed:", error);
        throw new Error(`Neuspešan mrežni zahtev ili obrada: ${error.message}`);
    }
};

export const complexQueryService = {
    fetchAccountRanking: async (statusName) => {
        const endpoint = `${BASE_URL}/account-ranking/${statusName}`;
        return executeGet(endpoint);
    },

    fetchLeadStatusSummary: async (lifecycleName) => {
        const endpoint = `${BASE_URL}/lead-status-summery/${lifecycleName}`;
        return executeGet(endpoint);
    },

    fetchLeadDensityRecommendation: async () => {
        const endpoint = `${BASE_URL}/lead-density-recommendation`;
        return executeGet(endpoint);
    },

    fetchLeadUpdateResult: async (fromStatus, toStatus) => {
        const endpoint = `${BASE_URL}/lead-update-result/${fromStatus}/${toStatus}`;
        return executeGet(endpoint);
    },

    fetchDeleteSummary: async (lifecycleId) => {
        const endpoint = `${BASE_URL}/delete-summery/${lifecycleId}`;
        return executeGet(endpoint);
    }
};