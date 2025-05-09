import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'

export const useCommentsData = (idLesson) => {
    const [commentsData, setCommentsData] = useState([])
    const [error, setError] = useState(false)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchData = async () => {
            if (!idLesson) return;
            setError(false)
            setLoading(true)

            const [errorData, data] = await safeAwait(
                axiosInstance.get(
                    `/api/courses/comments/${idLesson}`,
                ),
            )
            if (errorData) {
                setError(true)
                console.error(errorData)
                return
            }

            setCommentsData(data.data)

            setLoading(false)
        }
        fetchData()
    }, [idLesson])

    return { commentsData, error, loading }
}