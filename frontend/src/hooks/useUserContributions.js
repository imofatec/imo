import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react';

export const useUserContributions = (page, size) => {
    const [contributions, setContributions] = useState([]);

    useEffect(() => {
        if (page < 0 || size <= 0) return;

        const fetchContributions = async () => {
            const [error, response] = await safeAwait(
                authAxiosInstance.get('/api/user/contributions', {
                    params: { page, size },
                }),
            )

            if (error) {          
                return { error: error.response.data.message }
            }

            setContributions(response.data);
        };


        fetchContributions();
    }, [page, size]);

    return contributions;

}