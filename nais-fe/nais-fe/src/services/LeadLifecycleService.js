const BASE_URL = 'http://localhost:8081/api/lead-lifecycles';

export const leadLifecyclesService = {
    getAll: async() => {
        const response = await fetch(BASE_URL);
        if (!response) {
            throw new Error('Failed to fetch lead statuses');
        }
        return await response.json();
    }
}