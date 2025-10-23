const BASE_URL = 'http://localhost:8081/api/accounts';

export const accountService = {
    getProsti: async(number) => {
        const response = await fetch(`${BASE_URL}/more-then-n-leads/${number}`);
        if (!response) {
            throw new Error('Failed to fetch accounts');
        }
        return await response.json();
    }
}