import DragDrop from '@/components/ui/dragdrop'
import UserPicture from '@/components/ui/userpicture'
import { Separator } from '@/components/ui/separator'
import { Titulo } from '@/components/ui/titulo'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { Form, useActionData, redirect } from 'react-router-dom'
import { useEffect, useRef, useState } from 'react'
import useFetchUserInfo from '@/hooks/useFetchUserInfo'
import useImageUpload from '@/hooks/useImageUpload'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { updateUserRequest } from '@/requests/user/updateUserRequest'
import { useFormValidator } from '@/hooks/useFormValidator'
import { updateUserSchema } from '@/schemas/updateUserSchema'
import SkeletonAccountSettings from '@/components/skeletons/SkeletonAccountSettings'
import { useAuth } from '@/context/useAuth'

export default function AccountSettings() {
  const startUploadLoading = [true, false]
  const startCredentialsLoading = [false, true]
  const finishedLoading = [false, false]
  const [pageLoading, setPageLoading] = useState(true)
  const actionData = useActionData()
  const formRef = useRef()
  const { setUrlImage, userInfo, urlImage, fetchUserInfo } = useFetchUserInfo()
  const { handleImageUpload } = useImageUpload(setUrlImage)

  const [isLoading, setIsLoading] = useState([false, false])
  const [uploadError, setUploadError] = useState(null)
  const [credentialsError, setCredentialsError] = useState(null)
  const [successUpdated, setSuccessUpdated] = useState(null)
  const [selectedFile, setSelectedFile] = useState(null)

  const { authLoading } = useAuth()

  const { formData, fieldErrors, handleChange, setFormData, setFieldErrors } =
    useFormValidator(updateUserSchema)

  const clearErrors = () => {
    setSuccessUpdated(null)
    setUploadError(null)
    setCredentialsError(null)
  }

  const finishLoading = () => {
    setIsLoading(finishedLoading)
  }

  const handleUploadClick = async () => {
    if (!selectedFile) {
      finishLoading()
      return
    }

      const { error } = await handleImageUpload(selectedFile)

      if (error) {
        setUploadError(error)
        finishLoading()
        return
      }

      fetchUserInfo()
      setSelectedFile(null)
      finishLoading()
  }

  const handleActionData = () => {
    if (!actionData) {
      return
    }

    const { error, success } = actionData

    if (success === 204) {
      finishLoading()
      return
    }

    if (error) {
      setCredentialsError(error)
      finishLoading()
      return
    }

    setSuccessUpdated(success)
    formRef.current.reset()
    fetchUserInfo()
    finishLoading()
  }

  useEffect(() => {
      handleActionData()
  }, [actionData])

  useEffect(() => {
    const fetch = async () => {
      await fetchUserInfo()
      setPageLoading(false)
    }

    fetch()
  }, [])

  return (
    <>
      <Titulo titulo={'IMO / Configurar conta'}></Titulo>

      {pageLoading || authLoading ? (
        <SkeletonAccountSettings />
      ) : (
        <div className="h-screen mx-28 mt-16">
          <>
            <h1 className="font-semibold text-3xl">Configurações da conta</h1>
            <p className="text-sm p-3 text-white">
              Veja e edite informações sobre a sua conta IMO
            </p>
            <Separator className="m-4 bg-custom-border-gray"></Separator>
          </>

          <div className="flex flex-row">
            <div className="flex flex-col w-1/4 items-center justify-between mr-14">
              <div className="flex flex-col items-center gap-y-6">
                <UserPicture
                  size="xl"
                  profilePic={urlImage}
                  alt="Foto de Perfil"
                ></UserPicture>

                <DragDrop
                  onImageSelect={setSelectedFile}
                  selectedFile={selectedFile}
                  setImagePreview={setUrlImage}
                ></DragDrop>
              </div>

              <div className="space-y-6 text-center">
                <SpinnerButton
                  children="Editar foto"
                  isLoading={isLoading[0]}
                  onClick={() => {
                    setIsLoading(startUploadLoading),
                      handleUploadClick(),
                      clearErrors()
                  }}
                  className="w-[18rem] px-8 bg-custom-header-cyan text-black font-bold"
                />

                <p className="inline-flex justify-center w-4/5 h-4 text-red-500">
                  {uploadError && uploadError}
                </p>
              </div>
            </div>

            <Form
              method="put"
              action={updateUserRequest}
              ref={formRef}
              className="flex flex-col justify-between w-3/4 ml-14"
            >
              <div className="flex flex-row justify-center gap-x-14 w-full mt-10">
                <div className="w-full">
                  <InputLabel
                    label={'Nome'}
                    id="name"
                    name="name"
                    type="text"
                    placeholder={userInfo?.name}
                    value={formData.name || ''}
                    onChange={handleChange}
                    className={
                      fieldErrors.name
                        ? 'border-red-500 focus:border-red-500'
                        : ''
                    }
                  ></InputLabel>
                  {fieldErrors.name && (
                    <p className="text-sm text-red-500 !mt-0">
                      {fieldErrors.name}
                    </p>
                  )}
                </div>
                <div className="w-full">
                  <InputLabel
                    label={'E-mail'}
                    id="email"
                    name="email"
                    type="text"
                    placeholder={userInfo?.email}
                    value={formData.email || ''}
                    onChange={handleChange}
                    className={
                      fieldErrors.email
                        ? 'border-red-500 focus:border-red-500'
                        : ''
                    }
                  ></InputLabel>
                  {fieldErrors.email && (
                    <p className="text-sm text-red-500 !mt-0">
                      {fieldErrors.email}
                    </p>
                  )}
                </div>
              </div>

              <div className="flex flex-row justify-center gap-x-14 w-full">
                <div className="w-full">
                  <InputLabel
                    label={'Senha'}
                    id="password"
                    name="password"
                    type="password"
                    placeholder="Digite a nova senha"
                    value={formData.password || ''}
                    onChange={handleChange}
                    className={
                      fieldErrors.password
                        ? 'border-red-500 focus:border-red-500'
                        : ''
                    }
                  ></InputLabel>
                  {fieldErrors.password && (
                    <p className="text-sm text-red-500 !mt-0">
                      {fieldErrors.password}
                    </p>
                  )}
                </div>
                <div className="w-full">
                  <InputLabel
                    label="Confirme a senha"
                    id="confPassword"
                    name="confPassword"
                    type="password"
                    placeholder="Confirme a nova senha"
                    value={formData.confPassword || ''}
                    onChange={handleChange}
                    className={
                      fieldErrors.confPassword
                        ? 'border-red-500 focus:border-red-500'
                        : ''
                    }
                  ></InputLabel>
                  {fieldErrors.confPassword && (
                    <p className="text-sm text-red-500 !mt-0">
                      {fieldErrors.confPassword}
                    </p>
                  )}
                </div>
              </div>

              <div className="flex flex-col items-center mb-[0.80rem]">
                <SpinnerButton
                  children="Atualizar dados"
                  isLoading={isLoading[1]}
                  onClick={() => {
                    setIsLoading(startCredentialsLoading),
                      clearErrors(),
                      setSelectedFile(null)
                  }}
                  className="w-[18rem] bg-custom-header-cyan text-black font-bold"
                />
                <div className="flex justify-center pt-5">
                  <p className="h-4">
                    {credentialsError && (
                      <span className="text-red-500">{credentialsError}</span>
                    )}
                    {successUpdated && (
                      <span className="text-green-500">{successUpdated}</span>
                    )}
                  </p>
                </div>
              </div>
            </Form>
          </div>
        </div>
      )}
    </>
  )
}
