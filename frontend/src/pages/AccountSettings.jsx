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

const startUploadLoading = [true, false]
const startCredentialsLoading = [false, true]
const finishedLoading = [false, false]

export default function AccountSettings() {
  const actionData = useActionData()
  const formRef = useRef()
  const { setUrlImage, userInfo, urlImage, fetchUserInfo } = useFetchUserInfo()
  const { handleImageUpload } = useImageUpload(setUrlImage)

  const [isLoading, setIsLoading] = useState([false, false])
  const [uploadError, setUploadError] = useState(null)
  const [credentialsError, setCredentialsError] = useState(null)
  const [successUpdated, setSuccessUpdated] = useState(null)
  const [selectedFile, setSelectedFile] = useState(null)

  const clearErrors = () => {
    setSuccessUpdated(null)
    setUploadError(null)
    setCredentialsError(null)
  }

  const finishLoading = () => {
    setIsLoading(finishedLoading)
  }

  const handleUploadClick = () => {
    if (!selectedFile) {
      finishLoading()
      return
    }

    setTimeout(async () => {
      const { error } = await handleImageUpload(selectedFile)

      if (error) {
        setUploadError(error)
        finishLoading()
        return
      }

      fetchUserInfo()
      setSelectedFile(null)
      finishLoading()
    }, 400)
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
    setTimeout(() => {
      handleActionData()
    }, 400)
  }, [actionData])

  useEffect(() => {
    fetchUserInfo()
  }, [])

  return (
    <>
      <Titulo titulo={'IMO / Configurar conta'}></Titulo>
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
              <InputLabel
                label={'Nome'}
                id="name"
                name="name"
                type="text"
                placeholder={userInfo?.name}
              ></InputLabel>

              <InputLabel
                label={'E-mail'}
                id="email"
                name="email"
                type="text"
                placeholder={userInfo?.email}
              ></InputLabel>
            </div>

            <div className="flex flex-row justify-center gap-x-14 w-full">
              <InputLabel
                label={'Senha'}
                id="password"
                name="password"
                type="password"
                placeholder="Digite a nova senha"
              ></InputLabel>
              <InputLabel
                label="Confirme a senha"
                id="password-confirm"
                name="password-confirm"
                type="password"
                placeholder="Confirme a nova senha"
              ></InputLabel>
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
    </>
  )
}
