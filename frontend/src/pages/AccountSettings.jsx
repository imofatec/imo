import DragDrop from '@/components/ui/dragdrop'
import UserPicture from '@/components/ui/userpicture'
import { Button } from '@/components/ui/button'
import { Separator } from '@/components/ui/separator'
import { Titulo } from '@/components/ui/titulo'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { Form, useActionData } from 'react-router-dom'
import {  useEffect, useRef } from 'react'
import useFetchUserInfo from '@/hooks/useFetchUserInfo'
import useImageUpload from '@/hooks/useImageUpload'

export default function AccountSettings() {
  
  const actionData = useActionData()
  const formRef = useRef()
  const {setUrlImage,userInfo,urlImage,fetchUserInfo} = useFetchUserInfo();
  const {handleImageUpload} = useImageUpload(setUrlImage)
  

  useEffect(() => {
    if (actionData?.success) {
      formRef.current.reset()
      fetchUserInfo()
    }
  }, [actionData])

  return (
    <>
      <Titulo titulo={'IMO / Configurar'}></Titulo>

      <div className="flex flex-col h-screen m-16 mx-28">
        <h1 className="font-semibold text-3xl">Configurações da conta</h1>
        <p className="text-sm p-3 text-white">
          Veja e edite informações sobre a sua conta IMO
        </p>
        <Separator className="m-4 bg-custom-border-gray"></Separator>

        <div className="flex flex-row items-center">
          <div className="flex flex-col w-1/4 items-center mx-8">
            <div className="flex flex-col items-center gap-y-6">
              <UserPicture
                size="xl"
                profilePic={urlImage}
                alt="Foto de Perfil"
              ></UserPicture>

              <DragDrop onImageUpload={handleImageUpload}></DragDrop>
            </div>
            <Button className="bg-custom-header-cyan text-black font-bold px-8">
              Editar Foto de Perfil
            </Button>
          </div>

          <div className="flex flex-col w-3/4 mx-8">
            <Form method="post" action="/user/configurar-conta" ref={formRef}>
              <div className="flex flex-row gap-x-14">
                <InputLabel
                  label={'Username'}
                  id="username"
                  name="username"
                  type="text"
                  placeholder={userInfo?.username}
                ></InputLabel>

                <InputLabel
                  label={'E-mail'}
                  id="email"
                  name="email"
                  type="text"
                  placeholder={userInfo?.email}
                ></InputLabel>
              </div>
              <div className="flex flex-row gap-x-14">
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
              <div className="flex flex-row justify-center">
                <Button
                  type="submit"
                  className="bg-custom-header-cyan text-black font-bold "
                >
                  Atualizar Dados
                </Button>
              </div>
            </Form>
            {actionData && (
              <div className="flex justify-center pt-5">
                {actionData?.error && (
                  <p className="text-red-500">{actionData.error}</p>
                )}
                {actionData?.success && (
                  <p className="text-green-500">{actionData.success}</p>
                )}
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  )
}
