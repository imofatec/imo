import { View, Text, Pressable } from "react-native";
import FormInput from "../inputs/formInput";

export function CertificateCodeForm({ control, onSubmit, errorMessage }) {
    return (
        <View className="flex-1 bg-custom-primary px-6 justify-center">
            <Text className="text-white text-center text-lg font-semibold mb-8">Informe o codigo do certificado para verificar sua integridade</Text>

            <View className="gap-4">
                <FormInput control={control} name="id" label="Código do Certificado" placeholder="Digite o código aqui" autoCapitalize="none" maxLength={24}/>
                <Pressable className="bg-white py-3 rounded-full mb-6"
                    onPress={onSubmit}>
                    <Text className="text-black text-2xl text-center font-bold">Validar</Text>
                </Pressable>
                {errorMessage && (
          <Text className="text-red-500 text-center mb-4">{errorMessage}</Text>
        )}
            </View>
        </View>
    );
}
