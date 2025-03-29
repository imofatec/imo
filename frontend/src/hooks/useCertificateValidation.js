import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

const useCertificateValidation = () => {
  const fetchCertificateData = async (id) => {
    const [error, response] = await safeAwait(
      axiosInstance.get(`/api/user/certificate/${id}`),
    )

    if (error) {
      return error.response.data.message
        ? { error: error.response.data.message, success: null }
        : { error: 'Não foi possível validar o certificado', success: null }
    }

    return { error: null, success: response.data }
  }
  return { fetchCertificateData }
}

export default useCertificateValidation
