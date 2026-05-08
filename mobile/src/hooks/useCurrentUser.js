import { useEffect, useState, useCallback } from "react";
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";
import AsyncStorage from '@react-native-async-storage/async-storage';

export function useCurrentUser() {
    const [user, setUser] = useState(null);
    const [urlImage, setUrlImage] = useState(null); 
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchUser = useCallback(async () => {
        setLoading(true);
        const [err, data] = await safeAwait(
            apiFetch(`/api/user/profile`, {
                method: "GET",
            })
        );
        if (err) {
            setError(err.message);
            setLoading(false);
            return;
        }
        setUser(data || null);
        setError(null);
        if (data.profilePicturePath) {
            try {
                const token = await AsyncStorage.getItem("token");
                const imageUrl = data.profilePicturePath;

                const res = await fetch(imageUrl, {
                    method: "GET",
                    headers: {
                        Authorization: "Bearer " + token,
                    },
                });
                const blob = await res.blob();
                const reader = new FileReader();
                reader.onloadend = () => {
                    setUrlImage(reader.result); 
                };
                reader.readAsDataURL(blob);
            } catch (imgErr) {
                console.error("Erro ao buscar imagem:", imgErr);
            }
        } else {
            setUrlImage(null);
        }
        setLoading(false);
    }, []);

    useEffect(() => {
        fetchUser();
    }, [fetchUser]);

    return { user, urlImage, loading, error, refetch: fetchUser };
}
