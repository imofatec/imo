import { useState } from 'react'
import axios from 'axios'
import { safeAwait } from '@/lib/safeAwait'

const useCertificateValidation = () => {
  const [certificateData, setCertificateData] = useState()
  const [error, setError] = useState(null)

  const fetchCertificateData = async (id) => {
    const [error, response] = await safeAwait(
      axios.get(`/api/user/certificate/${id}`),
    )
    if (error) {
      setError('Erro ao buscar dados do certificado.')
      setCertificateData(null)
      return
    }
    setCertificateData(response.data)
    setError(null)
  }
  return { certificateData, error, fetchCertificateData }
}

export default useCertificateValidation
