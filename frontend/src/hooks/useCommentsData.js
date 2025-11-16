import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'

export const useCommentsData = (idLesson) => {
    const [commentsData, setCommentsData] = useState([])
    const [error, setError] = useState(false)
    const [loading, setLoading] = useState(true)


    const fetchData = async () => {
        if (!idLesson) return;
        setError(false)
        setLoading(true)

        const page = undefined
        const size = undefined

        const [errorData, data] = await safeAwait(
            axiosInstance.get(
                `/api/courses/comments/${idLesson}`,
                {
                    params: { page, size },
                },
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
    useEffect(() => {
        fetchData()
    }, [idLesson])
    return { commentsData, error, loading,refetchComments: fetchData }
}