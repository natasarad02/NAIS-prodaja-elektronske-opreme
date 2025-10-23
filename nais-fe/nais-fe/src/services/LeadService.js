const BASE_URL = 'http://localhost:8081/api/leads';

export const leadService = {
    getProsti: async(statusId) => {
        const response = await fetch(`${BASE_URL}/for-status/${statusId}`);
        if (!response) {
            throw new Error('Failed to fetch leads');
        }
        return await response.json();
    }
}