import { View, Text, Pressable } from "react-native";

export function CertificateDetails({ certificateData, onBack }) {
    return (
        <View className="flex-1 bg-custom-primary gap-4 px-6 justify-center">
            <View className="bg-white/10 rounded-lg p-6 w-full shadow-lg border border-white/20">
                <Text className="text-white text-center text-3xl font-extrabold mb-6">
                    Certificado Válido!
                </Text>

                {Object.entries({
                    "Nome do Aluno": certificateData.authorName,
                    "Nome do Curso": certificateData.courseName,
                    "Data de Início": certificateData.IniciationDate,
                    "Data de Conclusão": certificateData.completionDate,
                }).map(([label, value]) => (
                    <View key={label} className="bg-white/5 rounded-2xl p-4 mb-3 border border-white/10">
                        <Text className="text-white/70 text-sm">{label}</Text>
                        <Text className="text-white text-lg font-semibold">{value}</Text>
                    </View>
                ))}
            </View>

            <Pressable className="bg-white py-3 rounded-full mb-6" onPress={onBack}>
                <Text className="text-black text-2xl text-center font-bold">
                    Validar outro certificado
                </Text>
            </Pressable>
        </View>
    );
}
